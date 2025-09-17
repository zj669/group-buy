package com.zj.groupbuy.repository.trade.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.entity.GroupBuyOrder;
import com.zj.groupbuy.model.entity.GroupBuyOrderList;
import com.zj.groupbuy.model.entity.NotifyTask;
import com.zj.groupbuy.model.enums.ActivityStatusEnum;
import com.zj.groupbuy.model.enums.GroupBuyOrderEnum;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.model.enums.TradeOrderStatusEnum;
import com.zj.groupbuy.repository.mapper.GroupBuyActivityMapper;
import com.zj.groupbuy.repository.mapper.GroupBuyOrderListMapper;
import com.zj.groupbuy.repository.mapper.GroupBuyOrderMapper;
import com.zj.groupbuy.repository.mapper.NotifyTaskMapper;
import com.zj.groupbuy.repository.trade.ITradeRepository;

import com.zj.groupbuy.service.trade.model.aggregate.lock.GroupBuyOrderAggregate;
import com.zj.groupbuy.service.trade.model.aggregate.settlement.GroupBuyTeamSettlementAggregate;
import com.zj.groupbuy.service.trade.model.entity.lock.GroupBuyProgressVO;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.PayActivityEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.PayDiscountEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class TradeRepository implements ITradeRepository {
    @Resource
    private GroupBuyActivityMapper groupBuyActivityDao;
    @Resource
    private GroupBuyOrderMapper groupBuyOrderDao;
    @Resource
    private GroupBuyOrderListMapper groupBuyOrderListDao;
    @Resource
    private NotifyTaskMapper notifyTaskDao;
    @Override
    public GroupBuyActivity queryGroupBuyActivityEntityByActivityId(Long activityId) {

        return groupBuyActivityDao.selectOne(new LambdaQueryWrapper<GroupBuyActivity>().eq(GroupBuyActivity::getActivityId, activityId));
    }

    @Override
    public Long queryOrderCountByActivityId(Long activityId, String userId) {
        return groupBuyOrderListDao.selectCount(new LambdaQueryWrapper<GroupBuyOrderList>().eq(GroupBuyOrderList::getActivityId, activityId).eq(GroupBuyOrderList::getUserId, userId));
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.selectOne(new LambdaQueryWrapper<GroupBuyOrder>().eq(GroupBuyOrder::getTeamId,teamId));

        return GroupBuyProgressVO.builder()
                .completeCount(groupBuyOrder.getCompleteCount())
                .targetCount(groupBuyOrder.getTargetCount())
                .lockCount(groupBuyOrder.getLockCount())
                .build();
    }

    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOutOrder(String userId, String outTradeNo) {

        GroupBuyOrderList groupBuyOrderList =  groupBuyOrderListDao.selectOne(new LambdaQueryWrapper<GroupBuyOrderList>()
                .eq(GroupBuyOrderList::getUserId,userId)
                .eq(GroupBuyOrderList::getOutTradeNo,outTradeNo)
                .eq(GroupBuyOrderList::getStatus, TradeOrderStatusEnum.CREATE)
        );
        if(groupBuyOrderList == null){
            log.info("未查询到订单");
            return null;
        }
        return MarketPayOrderEntity.builder()
                .teamId(groupBuyOrderList.getTeamId())
                .orderId(groupBuyOrderList.getOrderId())
                .deductionPrice(groupBuyOrderList.getDeductionPrice())
                .tradeOrderStatusEnum(groupBuyOrderList.getStatus())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketPayOrderEntity lockPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) {
        UserEntity userEntity = groupBuyOrderAggregate.getUserEntity();
        PayActivityEntity payActivityEntity = groupBuyOrderAggregate.getPayActivityEntity();
        PayDiscountEntity payDiscountEntity = groupBuyOrderAggregate.getPayDiscountEntity();
        // 1 看用户是否第一次拼团
        String teamId = payActivityEntity.getTeamId();
        if(StringUtils.isBlank(teamId)){
            // 是则新建，则生成唯一的拼团id
            teamId = RandomStringUtils.randomNumeric(8);
            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                    .teamId(teamId)
                    .activityId(String.valueOf(payActivityEntity.getActivityId()))
                    .lockCount(1)
                    .channel(payDiscountEntity.getChannel())
                    .source(payDiscountEntity.getSource())
                    .completeCount(0)
                    .targetCount(payActivityEntity.getTargetCount())
                    .deductionPrice(payDiscountEntity.getDeductionPrice())
                    .originalPrice(payDiscountEntity.getOriginalPrice())
                    .payPrice(payDiscountEntity.getDeductionPrice())
                    .validStartTime(payActivityEntity.getStartTime())
                    .validEndTime(payActivityEntity.getEndTime())
                    .notifyUrl(payDiscountEntity.getNotify())
                    .notifyType(payDiscountEntity.getNotifyType())
                    .build();
            groupBuyOrderDao.insert(groupBuyOrder);
        }else{
            // 不是则看能否成团
            int i = groupBuyOrderDao.updateAddLockCount(teamId);
            if( i!= 1){
                throw new RuntimeException("拼团失败");
            }
        }
        // 保存详细信息
        String orderId = RandomStringUtils.randomNumeric(12);
        String bizId = RandomStringUtils.randomNumeric(12);
        GroupBuyOrderList groupBuyOrderListReq = GroupBuyOrderList.builder()
                .userId(userEntity.getUserId())
                .teamId(teamId)
                .orderId(orderId)
                .activityId(String.valueOf(payActivityEntity.getActivityId()))
                .startTime(payActivityEntity.getStartTime())
                .endTime(payActivityEntity.getEndTime())
                .goodsId(payDiscountEntity.getGoodsId())
                .source(payDiscountEntity.getSource())
                .channel(payDiscountEntity.getChannel())
                .originalPrice(payDiscountEntity.getDeductionPrice())
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .status(TradeOrderStatusEnum.CREATE)
                .outTradeNo(payDiscountEntity.getOutTradeNo())
                .bizId(bizId)
                .payPrice(payDiscountEntity.getDeductionPrice())
                .build();

        groupBuyOrderListDao.insert(groupBuyOrderListReq);
        return MarketPayOrderEntity.builder()
                .orderId(orderId)
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .tradeOrderStatusEnum(TradeOrderStatusEnum.CREATE)
                .build();
    }

    @Override
    public boolean isSCBlackIntercept(String source, String channel) {
        return false;
    }

    @Override
    public MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo) {
        GroupBuyOrderList groupBuyOrderListRes = groupBuyOrderListDao.selectOne(new LambdaQueryWrapper<GroupBuyOrderList>()
                .eq(GroupBuyOrderList::getUserId, userId)
                .eq(GroupBuyOrderList::getOutTradeNo, outTradeNo)
                .eq(GroupBuyOrderList::getStatus, TradeOrderStatusEnum.CREATE)
        );
        if (null == groupBuyOrderListRes) return null;

        return MarketPayOrderEntity.builder()
                .teamId(groupBuyOrderListRes.getTeamId())
                .orderId(groupBuyOrderListRes.getOrderId())
                .deductionPrice(groupBuyOrderListRes.getDeductionPrice())
                .tradeOrderStatusEnum(groupBuyOrderListRes.getStatus())
                .build();
    }

    @Override
    public GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.selectOne(new LambdaQueryWrapper<GroupBuyOrder>().eq(GroupBuyOrder::getTeamId,teamId));
        return GroupBuyTeamEntity.builder()
                .teamId(groupBuyOrder.getTeamId())
                .activityId(Long.valueOf(groupBuyOrder.getActivityId()))
                .targetCount(groupBuyOrder.getTargetCount())
                .completeCount(groupBuyOrder.getCompleteCount())
                .lockCount(groupBuyOrder.getLockCount())
                .status(groupBuyOrder.getStatus())
                .validStartTime(groupBuyOrder.getValidStartTime())
                .validEndTime(groupBuyOrder.getValidEndTime())
                .notify(groupBuyOrder.getNotifyUrl())
                .notifyType(groupBuyOrder.getNotifyType())
                .build();
    }

    @Transactional(timeout = 500)
    @Override
    public void settlementMarketPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate) {
        UserEntity userEntity = groupBuyTeamSettlementAggregate.getUserEntity();
        GroupBuyTeamEntity groupBuyTeamEntity = groupBuyTeamSettlementAggregate.getGroupBuyTeamEntity();
        TradePaySuccessEntity tradePaySuccessEntity = groupBuyTeamSettlementAggregate.getTradePaySuccessEntity();

        // 1. 更新拼团订单明细状态
        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setUserId(userEntity.getUserId());
        groupBuyOrderListReq.setOutTradeNo(tradePaySuccessEntity.getOutTradeNo());
        groupBuyOrderListReq.setOutTradeTime(tradePaySuccessEntity.getOutTradeTime());

        int updateOrderListStatusCount = groupBuyOrderListDao.updateOrderStatus2COMPLETE(groupBuyOrderListReq);
        if (1 != updateOrderListStatusCount) {
            throw new AppException(ResponseCode.UPDATE_ZERO);
        }

        // 2. 更新拼团达成数量
        int updateAddCount = groupBuyOrderDao.updateAddCompleteCount(groupBuyTeamEntity.getTeamId());
        if (1 != updateAddCount) {
            throw new AppException(ResponseCode.UPDATE_ZERO);
        }

        // 3. 更新拼团完成状态
        if (groupBuyTeamEntity.getTargetCount() - groupBuyTeamEntity.getCompleteCount() == 1) {
            int updateOrderStatusCount = groupBuyOrderDao.updateOrderStatus2COMPLETE(groupBuyTeamEntity.getTeamId());
            if (1 != updateOrderStatusCount) {
                throw new AppException(ResponseCode.UPDATE_ZERO);
            }

            // 查询拼团交易完成外部单号列表
            List<GroupBuyOrderList> groupBuyOrderLists = groupBuyOrderListDao.selectList(new LambdaQueryWrapper<GroupBuyOrderList>().eq(GroupBuyOrderList::getTeamId, groupBuyTeamEntity.getTeamId())
                    .eq(GroupBuyOrderList::getStatus, TradeOrderStatusEnum.COMPLETE)
            );
            List<String> outTradeNoList =groupBuyOrderLists.stream().map(GroupBuyOrderList::getOutTradeNo).toList();

            // 拼团完成写入回调任务记录
            NotifyTask notifyTask = new NotifyTask();
            notifyTask.setActivityId(String.valueOf(groupBuyTeamEntity.getActivityId()));
            notifyTask.setTeamId(groupBuyTeamEntity.getTeamId());
            notifyTask.setNotifyUrl(groupBuyTeamEntity.getNotify());
            notifyTask.setNotifyType(groupBuyTeamEntity.getNotifyType());
            notifyTask.setNotifyCount(0);
            notifyTask.setNotifyStatus(0);
            notifyTask.setParameterJson(JSON.toJSONString(new HashMap<String, Object>() {{
                put("teamId", groupBuyTeamEntity.getTeamId());
                put("outTradeNoList", outTradeNoList);
            }}));

            notifyTaskDao.insert(notifyTask);
        }
    }

    @Override
    public int updateNotifyTaskStatusError(NotifyTask notifyTask) {
        return notifyTaskDao.updateNotifyTaskStatusError(notifyTask);
    }

    @Override
    public int updateNotifyTaskStatusSuccess(NotifyTask notifyTask) {
        return notifyTaskDao.updateNotifyTaskStatusSuccess(notifyTask);
    }

    @Override
    public int updateNotifyTaskStatusRetry(NotifyTask notifyTask) {
        return notifyTaskDao.updateNotifyTaskStatusRetry(notifyTask);
    }

    @Override
    public List<NotifyTask> queryUnExecutedNotifyTaskList() {
        return notifyTaskDao.queryUnExecutedNotifyTaskList();
    }

    @Override
    public List<NotifyTask> queryUnExecutedNotifyTaskList(String teamId) {
        return notifyTaskDao.queryUnExecutedNotifyTaskListByTeamId(teamId);
    }
}
