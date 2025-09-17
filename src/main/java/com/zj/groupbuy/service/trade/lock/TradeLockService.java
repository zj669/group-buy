package com.zj.groupbuy.service.trade.lock;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.ITradeLockService;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory.DynamicContext;
import com.zj.groupbuy.service.trade.model.aggregate.lock.GroupBuyOrderAggregate;
import com.zj.groupbuy.service.trade.model.entity.lock.PayActivityEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.PayDiscountEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.GroupBuyProgressVO;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleFilterBackEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradeLockService implements ITradeLockService {
    @Resource
    private ITradeRepository tradeRepository;
    @Resource
    private TradeLockRuleFilterFactory tradeRuleFilterFactory;


    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOutOrder(String userId, String outTradeNo) {
        log.info("拼团交易-查询未支付营销订单:{} outTradeNo:{}", userId, outTradeNo);
        return tradeRepository.queryNoPayMarketPayOutOrder(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        log.info("拼团交易-查询拼单进度:{}", teamId);
        return tradeRepository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MarketPayOrderEntity lockPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {
        log.info("拼团交易-锁定营销优惠支付订单:{} activityId:{} goodsId:{}", userEntity.getUserId(), payActivityEntity.getActivityId(), payDiscountEntity.getGoodsId());
        ILogicLink<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> lockRuleFilterBackEntityILogicLink = tradeRuleFilterFactory.getFirstLogicLink();
        // 交易规则过滤
        TradeLockRuleFilterBackEntity tradeRuleFilterBackEntity = lockRuleFilterBackEntityILogicLink.apply(TradeLockRuleCommandEntity.builder()
                        .activityId(payActivityEntity.getActivityId())
                        .userId(userEntity.getUserId())
                        .build(),
                new TradeLockRuleFilterFactory.DynamicContext());

        // 已参与拼团量 - 用于构建数据库唯一索引使用，确保用户只能在一个活动上参与固定的次数
        Long userTakeOrderCount = tradeRuleFilterBackEntity.getUserTakeOrderCount();
        log.info("用户已参与拼团量:{}", userTakeOrderCount);
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .userTakeOrderCount(userTakeOrderCount)
                .build();
        return tradeRepository.lockPayOrder(groupBuyOrderAggregate);
    }
}
