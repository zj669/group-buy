package com.zj.groupbuy.service.activity.distinct;

import com.zj.groupbuy.model.enums.MarketPlanEnums;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;


public interface IDistinctStrategy {
    void distinct(DefaultActivityStrategyFactory.DynamicContext context);

    MarketPlanEnums getMarketPlanEnums();
}
