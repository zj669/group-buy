package com.zj.groupbuy.service.trade.lock.filter.factory;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.common.design.singletonresponsibilitychain.factory.AbstractLogicLinkFactory;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory.DynamicContext;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleFilterBackEntity;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TradeLockRuleFilterFactory extends AbstractLogicLinkFactory<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> {
    @Resource
    private List<ILogicLink<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity>> modelList;


    @Override
    public List<ILogicLink<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity>> getLogicLinks() {
        return modelList;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        private String teamStockKey = "group_buy_market_team_stock_key_";

        private GroupBuyActivity groupBuyActivity;

        private Long userTakeOrderCount;

        public String generateTeamStockKey(String teamId) {
            if (StringUtils.isBlank(teamId)) return null;
            return teamStockKey + groupBuyActivity.getActivityId() + "_" + teamId;
        }

        public String generateRecoveryTeamStockKey(String teamId) {
            if (StringUtils.isBlank(teamId)) return null;
            return teamStockKey + groupBuyActivity.getActivityId() + "_" + teamId + "_recovery";
        }

    }
}