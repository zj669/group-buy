package com.zj.groupbuy.service.trade.settlement;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.ITradeOrderSettlementService;
import com.zj.groupbuy.service.trade.model.aggregate.settlement.GroupBuyTeamSettlementAggregate;
import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySettlementEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory.DynamicContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradeOrderSettlementService implements ITradeOrderSettlementService {
    @Resource
    private ITradeRepository repository;
    @Resource
    private TradeSettlementRuleFilterFactory tradeSettlementRuleFilter;
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

}