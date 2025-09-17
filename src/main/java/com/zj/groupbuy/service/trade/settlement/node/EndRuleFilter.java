package com.zj.groupbuy.service.trade.settlement.node;


import com.zj.groupbuy.common.design.singletonresponsibilitychain.AbstractLogicLink;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory.DynamicContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(4)
public class EndRuleFilter extends AbstractLogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> {
    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) {
        log.info("结算规则过滤-结束节点{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // 获取上下文对象
        GroupBuyTeamEntity groupBuyTeamEntity = dynamicContext.getGroupBuyTeamEntity();

        // 返回封装数据
        return TradeSettlementRuleFilterBackEntity.builder()
                .teamId(groupBuyTeamEntity.getTeamId())
                .activityId(groupBuyTeamEntity.getActivityId())
                .targetCount(groupBuyTeamEntity.getTargetCount())
                .completeCount(groupBuyTeamEntity.getCompleteCount())
                .lockCount(groupBuyTeamEntity.getLockCount())
                .status(groupBuyTeamEntity.getStatus())
                .validStartTime(groupBuyTeamEntity.getValidStartTime())
                .validEndTime(groupBuyTeamEntity.getValidEndTime())
                .build();
    }
}
