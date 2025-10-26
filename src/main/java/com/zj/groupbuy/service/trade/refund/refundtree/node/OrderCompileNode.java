package com.zj.groupbuy.service.trade.refund.refundtree.node;

import com.zj.groupbuy.service.trade.refund.refundtree.base.IBaseHandler;
import com.zj.groupbuy.service.trade.refund.refundtree.facotory.RefundFactory;

public class OrderCompileNode extends AbsRefund{
    @Override
    public RefundFactory.RefundResponseEntity doHandle(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
        return null;
    }

    @Override
    protected IBaseHandler<RefundFactory.RefundRequestEntity, RefundFactory.RefundResponseEntity, RefundFactory.RefundContextEntity> route(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
        return null;
    }
}
