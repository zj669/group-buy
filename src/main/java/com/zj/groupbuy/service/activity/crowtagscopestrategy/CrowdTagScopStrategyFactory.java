package com.zj.groupbuy.service.activity.crowtagscopestrategy;

import com.zj.groupbuy.model.enums.CrowTagScopeEnum;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CrowdTagScopStrategyFactory {
    @Resource
    private List<ICrowdTagScopStrategy> crowdTagScopStrategyList;
    private Map<CrowTagScopeEnum, ICrowdTagScopStrategy> crowdTagScopStrategyMap;

    @PostConstruct
    public void init() {
        crowdTagScopStrategyMap = crowdTagScopStrategyList.stream().collect(Collectors.toMap(ICrowdTagScopStrategy::getTagScope, Function.identity()));
    }

    public ICrowdTagScopStrategy getStrategy(CrowTagScopeEnum tag){
        return crowdTagScopStrategyMap.get(tag);
    }
}
