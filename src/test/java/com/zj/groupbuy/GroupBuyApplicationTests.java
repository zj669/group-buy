package com.zj.groupbuy;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.groupbuy.common.design.ruletree.StrategyHandler;
import com.zj.groupbuy.model.entity.Sku;
import com.zj.groupbuy.repository.mapper.SkuMapper;
import com.zj.groupbuy.service.activity.model.entity.MarketProductEntity;
import com.zj.groupbuy.service.activity.model.entity.TrialBalanceEntity;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory.DynamicContext;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GroupBuyApplicationTests {
    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;

    @Test
    void contextLoads() throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                .userId("1")
                .goodsId("9890001")
                .source("s01")
                .channel("c01")
                .build();
        TrialBalanceEntity apply = trial.apply(marketProductEntity, new DynamicContext());
        System.out.println(JSON.toJSONString(apply));
    }

}
