package com.zj.groupbuy.service.activity.trial.node;

import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.repository.activity.IActivityRepository;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.AbstractGroupBuyMarketSupport;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SwitchNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private IActivityRepository repository;
    @Resource
    private TagNode tagNode;
    @Resource
    private ErrorNode errorNode;
    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("进入切量开关是否开启");
        return router(marketProductEntity,dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        // 根据用户ID切量
        String userId = marketProductEntity.getUserId();
        // 判断是否降级
        if (repository.downgradeSwitch()) {
            log.info("拼团活动降级拦截 {}", userId);
            return  errorNode;
        }

        // 切量范围判断
        if (!repository.cutRange(userId)) {
            log.info("拼团活动切量拦截 {}", userId);
            return  errorNode;
        }
        return tagNode;
    }
}
