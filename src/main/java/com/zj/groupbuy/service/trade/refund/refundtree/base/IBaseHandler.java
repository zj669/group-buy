package com.zj.groupbuy.service.trade.refund.refundtree.base;

public interface IBaseHandler<Request, Response, context> {

    Response handle(Request request, context context);

    Response router(Request request, context context);
}
