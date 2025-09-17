package com.zj.groupbuy.service.trade.settlement.factory;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.common.design.singletonresponsibilitychain.factory.AbstractLogicLinkFactory;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory.DynamicContext;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TradeSettlementRuleFilterFactory extends AbstractLogicLinkFactory<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> {
    @Resource
    private List<ILogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity >> modelList;


    @Override
    public List<ILogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity>> getLogicLinks() {
        return modelList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DynamicContext {
        // 订单营销实体对象
        private MarketPayOrderEntity marketPayOrderEntity;
        // 拼团组队实体对象
        private GroupBuyTeamEntity groupBuyTeamEntity;
    }
}
