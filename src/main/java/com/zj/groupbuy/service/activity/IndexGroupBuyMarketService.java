package com.zj.groupbuy.service.activity;

import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory.DynamicContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class IndexGroupBuyMarketService implements IIndexGroupBuyMarketService {
    @Resource
    private DefaultActivityStrategyFactory defaultActivityStragyFactory;
    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler = defaultActivityStragyFactory.trial();
        return strategyHandler.apply(marketProductEntity, new DynamicContext());
    }
}