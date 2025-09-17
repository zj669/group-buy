package com.zj.groupbuy.service.activity.trial.node;

import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.entity.GroupBuyDiscount;
import com.zj.groupbuy.model.enums.MarketPlanEnums;
import com.zj.groupbuy.repository.activity.IActivityRepository;
import com.zj.groupbuy.service.activity.distinct.DistinctStrategyFactory;
import com.zj.groupbuy.service.activity.distinct.IDistinctStrategy;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.AbstractGroupBuyMarketSupport;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private IActivityRepository repository;
    @Resource
    private DistinctStrategyFactory distinctStrategyFactory;
    @Resource
    private EndNode endNode;
    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("进入试算节点");
        GroupBuyDiscount discount = dynamicContext.getDiscount();
        if (discount == null){
            return router(marketProductEntity,dynamicContext);
        }
        MarketPlanEnums marketPlan = discount.getMarketPlan();
        IDistinctStrategy strategy = distinctStrategyFactory.getStrategy(marketPlan);
        strategy.distinct(dynamicContext);
        return router(marketProductEntity,dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }

    @Override
    protected void multiThread(MarketProductEntity requestParams, DefaultActivityStrategyFactory.DynamicContext context) {
        GroupBuyActivity activity = context.getActivity();
        String discountId = activity.getDiscountId();
        GroupBuyDiscount discount = repository.selectDiscountById(discountId);
        context.setDiscount(discount);
    }
}
