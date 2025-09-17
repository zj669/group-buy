package com.zj.groupbuy.service.activity.crowtagscopestrategy;

import com.zj.groupbuy.model.enums.CrowTagScopeEnum;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;

public interface ICrowdTagScopStrategy {

    void apply( DefaultActivityStrategyFactory.DynamicContext dynamicContext);

    CrowTagScopeEnum getTagScope();
}
