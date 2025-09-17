package com.zj.groupbuy.service.trade.settlement;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.model.entity.NotifyTask;
import com.zj.groupbuy.model.enums.NotifyTaskHTTPEnum;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.ITradeOrderSettlementService;
import com.zj.groupbuy.service.trade.model.aggregate.settlement.GroupBuyTeamSettlementAggregate;
import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySettlementEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.notify.INotifyStrategy;
import com.zj.groupbuy.service.trade.notify.factory.DefaultNotifyFactory;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory.DynamicContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TradeOrderSettlementService implements ITradeOrderSettlementService {
    @Resource
    private ITradeRepository repository;
    @Resource
    private TradeSettlementRuleFilterFactory tradeSettlementRuleFilter;
    @Resource
    private DefaultNotifyFactory notifyFactory;
    @Override
    public TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) {
        log.info("拼团交易-支付订单结算:{} outTradeNo:{}", tradePaySuccessEntity.getUserId(), tradePaySuccessEntity.getOutTradeNo());
        ILogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> tradeSettlementRuleFilterFirstLogicLink = tradeSettlementRuleFilter.getFirstLogicLink();
        // 1. 结算规则过滤
        TradeSettlementRuleFilterBackEntity tradeSettlementRuleFilterBackEntity = tradeSettlementRuleFilterFirstLogicLink.apply(
                TradeSettlementRuleCommandEntity.builder()
                        .source(tradePaySuccessEntity.getSource())
                        .channel(tradePaySuccessEntity.getChannel())
                        .userId(tradePaySuccessEntity.getUserId())
                        .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                        .outTradeTime(tradePaySuccessEntity.getOutTradeTime())
                        .build(),
                new TradeSettlementRuleFilterFactory.DynamicContext());

        String teamId = tradeSettlementRuleFilterBackEntity.getTeamId();

        // 2. 查询组团信息
        GroupBuyTeamEntity groupBuyTeamEntity = GroupBuyTeamEntity.builder()
                .teamId(tradeSettlementRuleFilterBackEntity.getTeamId())
                .activityId(tradeSettlementRuleFilterBackEntity.getActivityId())
                .targetCount(tradeSettlementRuleFilterBackEntity.getTargetCount())
                .completeCount(tradeSettlementRuleFilterBackEntity.getCompleteCount())
                .lockCount(tradeSettlementRuleFilterBackEntity.getLockCount())
                .status(tradeSettlementRuleFilterBackEntity.getStatus())
                .validStartTime(tradeSettlementRuleFilterBackEntity.getValidStartTime())
                .validEndTime(tradeSettlementRuleFilterBackEntity.getValidEndTime())
                .build();

        // 3. 构建聚合对象
        GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate = GroupBuyTeamSettlementAggregate.builder()
                .userEntity(UserEntity.builder().userId(tradePaySuccessEntity.getUserId()).build())
                .groupBuyTeamEntity(groupBuyTeamEntity)
                .tradePaySuccessEntity(tradePaySuccessEntity)
                .build();

        // 4. 拼团交易结算
        repository.settlementMarketPayOrder(groupBuyTeamSettlementAggregate);

        // 5. 返回结算信息 - 公司中开发这样的流程时候，会根据外部需要进行值的设置
        return TradePaySettlementEntity.builder()
                .source(tradePaySuccessEntity.getSource())
                .channel(tradePaySuccessEntity.getChannel())
                .userId(tradePaySuccessEntity.getUserId())
                .teamId(teamId)
                .activityId(groupBuyTeamEntity.getActivityId())
                .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                .build();
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob() {
        log.info("拼团交易-执行结算通知任务");

        // 查询未执行任务
        List<NotifyTask> notifyTaskEntityList = repository.queryUnExecutedNotifyTaskList();

        return execSettlementNotifyJob(notifyTaskEntityList);
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob(String teamId) {
        log.info("拼团交易-执行结算通知回调，指定 teamId:{}", teamId);
        List<NotifyTask> notifyTaskEntityList = repository.queryUnExecutedNotifyTaskList(teamId);
        return execSettlementNotifyJob(notifyTaskEntityList);
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob(List<NotifyTask> notifyTaskEntityList) {
        int successCount = 0, errorCount = 0, retryCount = 0;
        for (NotifyTask notifyTask : notifyTaskEntityList) {
            try {
                // 回调处理 success 成功，error 失败
                INotifyStrategy notifyStrategy = notifyFactory.getNotifyStrategy(notifyTask.getNotifyType());
                String response = notifyStrategy.notify(notifyTask);

                // 更新状态判断&变更数据库表回调任务状态
                if (NotifyTaskHTTPEnum.SUCCESS.getValue().equals(response)) {
                    int updateCount = repository.updateNotifyTaskStatusSuccess(notifyTask);
                    if (1 == updateCount) {
                        successCount += 1;
                    }
                } else if (NotifyTaskHTTPEnum.ERROR.getValue().equals(response)) {
                    if (notifyTask.getNotifyCount() < 5) {
                        int updateCount = repository.updateNotifyTaskStatusError(notifyTask);
                        if (1 == updateCount) {
                            errorCount += 1;
                        }
                    } else {
                        int updateCount = repository.updateNotifyTaskStatusRetry(notifyTask);
                        if (1 == updateCount) {
                            retryCount += 1;
                        }
                    }
                }
            } catch (Exception e) {
                log.error("拼团交易-执行结算通知回调异常", e);
                errorCount += 1;
            }
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("waitCount", notifyTaskEntityList.size());
        resultMap.put("successCount", successCount);
        resultMap.put("errorCount", errorCount);
        resultMap.put("retryCount", retryCount);

        return resultMap;
    }

}