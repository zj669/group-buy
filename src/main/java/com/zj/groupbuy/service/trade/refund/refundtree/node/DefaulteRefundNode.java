package com.zj.groupbuy.service.trade.refund.refundtree.node;

import com.zj.groupbuy.service.trade.refund.refundtree.base.IBaseHandler;
import com.zj.groupbuy.service.trade.refund.refundtree.facotory.RefundFactory;

public class DefaulteRefundNode implements IBaseHandler<RefundFactory.RefundRequestEntity, RefundFactory.RefundResponseEntity,
        RefundFactory.RefundContextEntity> {

    @Override
    public RefundFactory.RefundResponseEntity handle(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
        return null;
    }

    @Override
    public RefundFactory.RefundResponseEntity router(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
        return null;
    }


}
