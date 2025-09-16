package com.zj.groupbuy.service.activity.trial.node;

import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.AbstractGroupBuyMarketSupport;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("trialRootNode")
@Slf4j
public class RootNode extends AbstractGroupBuyMarketSupport {
    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return null;
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return null;
    }
}
