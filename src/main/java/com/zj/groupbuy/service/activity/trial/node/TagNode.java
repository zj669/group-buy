package com.zj.groupbuy.service.activity.trial.node;

import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.model.entity.CrowdTagsDetail;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.enums.CrowTagScopeEnum;
import com.zj.groupbuy.repository.activity.IActivityRepository;
import com.zj.groupbuy.service.activity.crowtagscopestrategy.CrowdTagScopStrategyFactory;
import com.zj.groupbuy.service.activity.crowtagscopestrategy.ICrowdTagScopStrategy;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.AbstractGroupBuyMarketSupport;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TagNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private IActivityRepository repository;
    @Resource
    private CrowdTagScopStrategyFactory crowdTagScopStrategyFactory;
    @Resource
    private MarketNode marketNode;
    @Resource
    private ErrorNode errorNode;
    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("进入人群标签节点，判断用户是否可见这个活动");
        return router(marketProductEntity,dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        if(Boolean.TRUE.equals(dynamicContext.getIsVisible())){
            return marketNode;
        }
        return errorNode;
    }

    @Override
    protected void multiThread(MarketProductEntity requestParams, DefaultActivityStrategyFactory.DynamicContext context) {
        GroupBuyActivity activity = context.getActivity();
        String tagId = activity.getTagId();
        if(tagId == null){
            log.info("无人群限制");
            context.setIsVisible(true);
            context.setIsEnable(true);
            return;
        }
        CrowdTagsDetail crowdTagsDetail = repository.selectCrowdTagsDetailByUserId(requestParams.getUserId());
        if(crowdTagsDetail == null){
            log.info("用户没有标签");
            CrowTagScopeEnum tagScope = activity.getTagScope();
            ICrowdTagScopStrategy strategy = crowdTagScopStrategyFactory.getStrategy(tagScope);
            strategy.apply(context);
            return;
        }
        if(!tagId.equals(crowdTagsDetail.getTagId())){
            log.info("用户标签不符合，采用默认策略");
            CrowTagScopeEnum tagScope = activity.getTagScope();
            ICrowdTagScopStrategy strategy = crowdTagScopStrategyFactory.getStrategy(tagScope);
            strategy.apply(context);
            return;
        }
        context.setIsVisible(true);
        context.setIsEnable(true);
    }
}
