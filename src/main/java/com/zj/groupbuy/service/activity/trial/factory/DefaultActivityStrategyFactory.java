package com.zj.groupbuy.service.activity.trial.factory;


import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.entity.GroupBuyDiscount;
import com.zj.groupbuy.model.entity.Sku;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.node.RootNode;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultActivityStrategyFactory {
    private final RootNode trialRootNode;
    public DefaultActivityStrategyFactory(RootNode trialRootNode) {
        this.trialRootNode = trialRootNode;
    }

    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> trial(){
        return trialRootNode;
    }


    @Data
    public static class DynamicContext{

        /**
         * 是否可见
         */
        private Boolean isVisible;

        /**
         * 是否可参与
         */
        private Boolean isEnable;

        private GroupBuyActivity activity;
        private Sku sku;
        private GroupBuyDiscount discount;
        private BigDecimal deductionPrice;

    }
}
