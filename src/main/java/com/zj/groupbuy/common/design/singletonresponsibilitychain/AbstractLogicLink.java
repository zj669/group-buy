package com.zj.groupbuy.common.design.singletonresponsibilitychain;

/**
 * 抽象责任链节点逻辑
 *
 * @author QingshanWu
 * @date 2025/07/08
 */
public abstract class AbstractLogicLink<REQUEST, CONTEXT, RESULT> implements ILogicLink<REQUEST, CONTEXT, RESULT> {

    private ILogicLink<REQUEST, CONTEXT, RESULT> next;


    @Override
    public RESULT apply(REQUEST request, CONTEXT dynamicContext) {
        RESULT ans = doApply(request, dynamicContext);
        if(null != next){
            return next.apply(request, dynamicContext);
        }
        return ans;
    }

    protected abstract RESULT doApply(REQUEST request, CONTEXT dynamicContext);

    @Override
    public ILogicLink<REQUEST, CONTEXT, RESULT> next() {
        return next;
    }

    @Override
    public ILogicLink<REQUEST, CONTEXT, RESULT> appendNext(ILogicLink<REQUEST, CONTEXT, RESULT> next) {
        this.next = next;
        return next;
    }

    protected RESULT next(REQUEST request, CONTEXT dynamicContext) {
        return next.apply(request, dynamicContext);
    }

}
