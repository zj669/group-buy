package com.zj.groupbuy.service.activity.distinct;

import com.zj.groupbuy.model.enums.MarketPlanEnums;
import com.zj.groupbuy.service.activity.distinct.impl.DefaultDistinctStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DistinctStrategyFactory {
    @Resource
    private DefaultDistinctStrategy defaultDistinctStrategy;
    @Resource
    private List<IDistinctStrategy> distinctStrategyList;
    private Map<MarketPlanEnums, IDistinctStrategy> distinctStrategyMap;

    @PostConstruct
    public void init() {
        distinctStrategyMap = distinctStrategyList.stream().collect(
                Collectors.toMap(IDistinctStrategy::getMarketPlanEnums, Function.identity()));
    }

    public IDistinctStrategy getStrategy(MarketPlanEnums marketPlanEnums) {
        if(marketPlanEnums == null || !distinctStrategyMap.containsKey(marketPlanEnums)){
            log.error("未找到对应的优惠策略 marketPlanEnums:{} , 使用默认策略",marketPlanEnums);
            return defaultDistinctStrategy;
        }
        return distinctStrategyMap.get(marketPlanEnums);
    }
}
