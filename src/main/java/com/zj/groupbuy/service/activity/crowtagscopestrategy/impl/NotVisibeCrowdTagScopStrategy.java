package com.zj.groupbuy.service.activity.crowtagscopestrategy.impl;

import com.zj.groupbuy.model.enums.CrowTagScopeEnum;
import com.zj.groupbuy.service.activity.crowtagscopestrategy.ICrowdTagScopStrategy;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory.DynamicContext;
import org.springframework.stereotype.Component;

@Component
public class NotVisibeCrowdTagScopStrategy implements ICrowdTagScopStrategy {
    @Override
    public void apply(DynamicContext dynamicContext) {
        dynamicContext.setIsVisible(false);
        dynamicContext.setIsEnable(false);
    }

    @Override
    public CrowTagScopeEnum getTagScope() {
        return CrowTagScopeEnum.NOTVISIBLE;
    }
}
