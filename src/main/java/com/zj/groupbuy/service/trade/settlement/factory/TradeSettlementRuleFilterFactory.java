package com.zj.groupbuy.service.trade.settlement.factory;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.common.design.singletonresponsibilitychain.factory.AbstractLogicLinkFactory;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory.DynamicContext;
import com.zj.groupbuy.service.trade.settlement.node.EndRuleFilter;
import com.zj.groupbuy.service.trade.settlement.node.OutTradeNoRuleFilter;
import com.zj.groupbuy.service.trade.settlement.node.SCRuleFilter;
import com.zj.groupbuy.service.trade.settlement.node.SettableRuleFilter;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class TradeSettlementRuleFilterFactory extends AbstractLogicLinkFactory<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> {
    @Resource
    private SCRuleFilter scRuleFilter;
    @Resource
    private OutTradeNoRuleFilter outTradeNoRuleFilter;
    @Resource
    private SettableRuleFilter settableRuleFilter;
    @Resource
    private EndRuleFilter endRuleFilter;


    @Override
    public List<ILogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity>> getLogicLinks() {
        LinkedList<ILogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity>> logicLinkList = new LinkedList<>();
        logicLinkList.addLast(scRuleFilter);
        logicLinkList.addLast(outTradeNoRuleFilter);
        logicLinkList.addLast(settableRuleFilter);
        logicLinkList.addLast(endRuleFilter);
        return logicLinkList;
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
