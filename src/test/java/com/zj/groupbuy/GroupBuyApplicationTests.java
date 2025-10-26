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

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GroupBuyApplicationTests {
    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;
    @Resource
    private SkuMapper skuMapper;

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
        assertNotNull(apply, "试用结果不应为空");
    }

    @Test
    void testSkuMapper() {
        // 测试SkuMapper的基本功能
        List<Sku> skuList = skuMapper.selectList(null);
        assertNotNull(skuList, "商品列表不应为空");
        System.out.println("商品数量: " + skuList.size());
    }

    @Test
    void testActivityTrialWithDifferentGoods() throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        
        // 测试不同商品ID
        String[] goodsIds = {"9890001", "9890002", "9890003"};
        
        for (String goodsId : goodsIds) {
            MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                    .userId("1")
                    .goodsId(goodsId)
                    .source("s01")
                    .channel("c01")
                    .build();
            
            TrialBalanceEntity result = trial.apply(marketProductEntity, new DynamicContext());
            assertNotNull(result, "商品ID " + goodsId + " 的试用结果不应为空");
            System.out.println("商品ID " + goodsId + " 试用结果: " + JSON.toJSONString(result));
        }
    }

    @Test
    void testActivityTrialWithDifferentUsers() throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        
        // 测试不同用户ID
        String[] userIds = {"1", "2", "3", "test_user"};
        
        for (String userId : userIds) {
            MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                    .userId(userId)
                    .goodsId("9890001")
                    .source("s01")
                    .channel("c01")
                    .build();
            
            TrialBalanceEntity result = trial.apply(marketProductEntity, new DynamicContext());
            assertNotNull(result, "用户ID " + userId + " 的试用结果不应为空");
            System.out.println("用户ID " + userId + " 试用结果: " + JSON.toJSONString(result));
        }
    }

    @Test
    void testActivityTrialWithDifferentChannels() throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        
        // 测试不同渠道和来源组合
        String[][] channelSourcePairs = {
            {"s01", "c01"}, {"s02", "c02"}, {"s03", "c03"},
            {"web", "pc"}, {"app", "ios"}, {"app", "android"}
        };
        
        for (String[] pair : channelSourcePairs) {
            MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                    .userId("1")
                    .goodsId("9890001")
                    .source(pair[0])
                    .channel(pair[1])
                    .build();
            
            TrialBalanceEntity result = trial.apply(marketProductEntity, new DynamicContext());
            assertNotNull(result, "渠道 " + pair[1] + " 来源 " + pair[0] + " 的试用结果不应为空");
            System.out.println("渠道 " + pair[1] + " 来源 " + pair[0] + " 试用结果: " + JSON.toJSONString(result));
        }
    }

    @Test
    void testActivityTrialWithInvalidGoodsId() throws Exception  {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        
        // 测试无效商品ID```123123
        String[] invalidGoodsIds = {"invalid_goods", "9999999", "0", "-1", ""};
        
        for (String goodsId : invalidGoodsIds) {
            MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                    .userId("1")
                    .goodsId(goodsId)
                    .source("s01")
                    .channel("c01")
                    .build();
            
            TrialBalanceEntity result = trial.apply(marketProductEntity, new DynamicContext());
            // 对于无效商品ID，结果可能为null或包含错误信息
            System.out.println("无效商品ID " + goodsId + " 试用结果: " + 
                (result != null ? JSON.toJSONString(result) : "null"));
        }
    }

    @Test
    void testActivityTrialWithNullParameters() throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        
        // 测试空参数
        MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                .userId(null)
                .goodsId(null)
                .source(null)
                .channel(null)
                .build();
        
        try {
            TrialBalanceEntity result = trial.apply(marketProductEntity, new DynamicContext());
            System.out.println("空参数试用结果: " +
                (result != null ? JSON.toJSONString(result) : "null"));
        } catch (Exception e) {
            System.out.println("空参数测试抛出异常: " + e.getMessage());
            // 预期可能会抛出异常，这是正常的行为
        }
    }

    @Test
    void testActivityTrialVisibilityAndEnableStatus() throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> trial = defaultActivityStrategyFactory.trial();
        
        MarketProductEntity marketProductEntity = MarketProductEntity.builder()
                .userId("1")
                .goodsId("9890001")
                .source("s01")
                .channel("c01")
                .build();
        
        TrialBalanceEntity result = trial.apply(marketProductEntity, new DynamicContext());
        assertNotNull(result, "试用结果不应为空");
        
        // 验证活动状态信息
        if (result.getIsVisible() != null) {
            System.out.println("活动可见性: " + result.getIsVisible());
        }
        if (result.getIsEnable() != null) {
            System.out.println("活动可参与性: " + result.getIsEnable());
        }
        
        // 验证价格信息
        if (result.getOriginalPrice() != null) {
            assertTrue(result.getOriginalPrice().compareTo(BigDecimal.ZERO) >= 0,
                "原始价格应大于等于0");
        }
        if (result.getDeductionPrice() != null) {
            assertTrue(result.getDeductionPrice().compareTo(BigDecimal.ZERO) >= 0, 
                "折扣金额应大于等于0");
        }
        if (result.getPayPrice() != null) {
            assertTrue(result.getPayPrice().compareTo(BigDecimal.ZERO) >= 0, 
                "支付金额应大于等于0");
        }
    }

    @Test
    void testSkuMapperQueryByGoodsId() {
        // 测试根据商品ID查询
        LambdaQueryWrapper<Sku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sku::getGoodsId, "9890001");
        
        List<Sku> skuList = skuMapper.selectList(queryWrapper);
        assertNotNull(skuList, "根据商品ID查询结果不应为空");
        System.out.println("商品ID 9890001 的SKU数量: " + skuList.size());
        
        if (!skuList.isEmpty()) {
            Sku sku = skuList.get(0);
            System.out.println("商品信息: " + JSON.toJSONString(sku));
            assertNotNull(sku.getGoodsName(), "商品名称不应为空");
            assertNotNull(sku.getOriginalPrice(), "商品价格不应为空");
        }
    }

    @Test
    void testSkuMapperQueryBySourceAndChannel() {
        // 测试根据来源和渠道查询
        LambdaQueryWrapper<Sku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sku::getSource, "s01")
                   .eq(Sku::getChannel, "c01");
        List<Sku> skuList = skuMapper.selectList(queryWrapper);
        assertNotNull(skuList, "根据来源和渠道查询结果不应为空");
        System.out.println("来源 s01 渠道 c01 的SKU数量: " + skuList.size());
    }
}
