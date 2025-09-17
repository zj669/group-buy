package com.zj.groupbuy.service.activity.distinct.impl;

import com.zj.groupbuy.model.enums.MarketPlanEnums;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory.DynamicContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class DefaultDistinctStrategy extends AbstractDistinctStrategy{
    @Override
    public BigDecimal calculate(DynamicContext context, String split) {
        return context.getSku().getOriginalPrice();
    }

    @Override
    public MarketPlanEnums getMarketPlanEnums() {
        return null;
    }
}
