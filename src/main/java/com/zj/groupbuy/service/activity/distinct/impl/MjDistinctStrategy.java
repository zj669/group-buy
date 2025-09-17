package com.zj.groupbuy.service.activity.distinct.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.groupbuy.model.entity.Sku;
import com.zj.groupbuy.model.enums.MarketPlanEnums;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory.DynamicContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class MjDistinctStrategy extends AbstractDistinctStrategy {

    @Override
    public BigDecimal calculate(DynamicContext context, String split) {
        ObjectMapper objectMapper = new ObjectMapper();
        Sku sku = context.getSku();
        try {
            MJModel mjModel = objectMapper.readValue(split, MJModel.class);
            if(sku.getOriginalPrice().compareTo( mjModel.beginPrice)<0){
                return sku.getOriginalPrice();
            }
            log.info("开始进行满减优惠");
            BigDecimal originalPrice = sku.getOriginalPrice();
            BigDecimal curPrice = originalPrice.subtract(mjModel.reducePrice);
            if(curPrice.compareTo(BigDecimal.ZERO) < 0){
                curPrice  = new BigDecimal("0.01");
            }
            return curPrice;
        } catch (JsonProcessingException e) {
            log.error("MJ模型转换异常");
            return sku.getOriginalPrice();
        }
    }

    @Override
    public MarketPlanEnums getMarketPlanEnums() {
        return MarketPlanEnums.MJ;
    }


    @Data
    public static class MJModel{
        private BigDecimal beginPrice;
        private BigDecimal reducePrice;
    }

}
