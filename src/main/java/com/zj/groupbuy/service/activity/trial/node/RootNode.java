package com.zj.groupbuy.service.activity.trial.node;

import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.entity.Sku;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.repository.activity.IActivityRepository;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.AbstractGroupBuyMarketSupport;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("trialRootNode")
@Slf4j
public class RootNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private IActivityRepository repository;
    @Resource
    private SwitchNode switchNode;
    @Resource
    private ErrorNode errorNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("开始进入试算，现在开始校验数据");
        return router(marketProductEntity,dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        if(dynamicContext.getActivity() == null|| dynamicContext.getSku() == null){
            return errorNode;
        }
        return switchNode;
    }

    @Override
    protected void multiThread(MarketProductEntity requestParams, DefaultActivityStrategyFactory.DynamicContext context) {
        GroupBuyActivity activity = repository.selectActivityByGoodsId(requestParams.getGoodsId(),requestParams.getChannel(),requestParams.getSource());
        Sku sku = repository.selectSkuByIdAndSC(requestParams.getGoodsId(),requestParams.getChannel(),requestParams.getSource());
        if(activity == null || sku == null){
            log.error("活动不存在或者商品不存在");
        }
        context.setActivity(activity);
        context.setSku(sku);
    }
}
