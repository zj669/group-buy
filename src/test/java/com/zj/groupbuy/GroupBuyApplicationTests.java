package com.zj.groupbuy;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.groupbuy.model.entity.Sku;
import com.zj.groupbuy.repository.mapper.SkuMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GroupBuyApplicationTests {
    @Resource
    private SkuMapper skuMapper;

    @Test
    void contextLoads() {
        List<Sku> skus = skuMapper.selectList(new LambdaQueryWrapper<>());
        skus.forEach(a->{
            System.out.println(JSON.toJSONString(a));
        });
    }

}
