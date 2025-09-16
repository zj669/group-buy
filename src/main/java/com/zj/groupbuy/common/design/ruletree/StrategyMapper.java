package com.zj.groupbuy.common.design.ruletree;

/**
 * 规则树mapper
 *
 * @author: wuqingshan
 * @since : 2025年6月26日 15:12:12
 */
public interface StrategyMapper<Request, Context, Result> {

    /**
     * 获取待执行策略
     *
     * @param request        入参
     * @param dynamicContext 上下文
     * @return 返参
     * @throws Exception 异常
     */
    StrategyHandler<Request, Context, Result> get(Request request, Context dynamicContext) throws Exception;
}
