package com.zj.groupbuy.service.activity.distinct.impl;


import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.entity.GroupBuyDiscount;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.service.activity.distinct.IDistinctStrategy;
import com.zj.groupbuy.service.activity.trial.factory.DefaultActivityStrategyFactory;

import java.math.BigDecimal;

public abstract class AbstractDistinctStrategy implements IDistinctStrategy {

    public void distinct(DefaultActivityStrategyFactory.DynamicContext context){
        GroupBuyDiscount discount = context.getDiscount();
        String split = discount.getMarketExpr();
        if(split == null){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),"优惠配置错误");
        }
        BigDecimal curPrice = calculate(context, split);
        context.setDeductionPrice(curPrice);
    }

    public abstract BigDecimal calculate(DefaultActivityStrategyFactory.DynamicContext context, String split);
}
