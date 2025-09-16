package com.zj.groupbuy.common.design.strategymode;


import com.zj.groupbuy.common.model.IBaseEnum;

/**
 * 通用策略模式
 *
 * @author: wuqingshan
 * @since : 2025年6月26日 15:12:12
 */
public interface StrategyMapper {
    /**
     * 策略处理的类型
     *
     * @return IBaseEnum 策略处理的类型
     **/
    IBaseEnum getType();
}