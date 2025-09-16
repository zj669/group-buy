package com.zj.groupbuy.service.activity.trial;

import com.zj.groupbuy.common.design.ruletree.AbstractMultiThreadStrategyRouter;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;


public abstract class AbstractGroupBuyMarketSupport extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {
    @Override
    protected void multiThread(MarketProductEntity requestParams, DefaultActivityStrategyFactory.DynamicContext context) {

    }
}