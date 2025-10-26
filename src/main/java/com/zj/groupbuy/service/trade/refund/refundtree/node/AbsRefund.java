package com.zj.groupbuy.service.trade.refund.refundtree.node;

import com.zj.groupbuy.service.trade.refund.refundtree.base.IBaseHandler;
import com.zj.groupbuy.service.trade.refund.refundtree.facotory.RefundFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public abstract class AbsRefund implements IBaseHandler<RefundFactory.RefundRequestEntity, RefundFactory.RefundResponseEntity,
        RefundFactory.RefundContextEntity> {
    @Resource
    private DefaulteRefundNode defaulteRefundNode;

    @Override
    public RefundFactory.RefundResponseEntity handle(RefundFactory.RefundRequestEntity refundRequestEntity,
                                                    RefundFactory.RefundContextEntity refundContextEntity) {
        init(refundRequestEntity, refundContextEntity);
        return doHandle(refundRequestEntity, refundContextEntity);
    }

    @Override
    public   RefundFactory.RefundResponseEntity router(RefundFactory.RefundRequestEntity refundRequestEntity,
                                                       RefundFactory.RefundContextEntity refundContextEntity) {
        IBaseHandler<RefundFactory.RefundRequestEntity, RefundFactory.RefundResponseEntity,
                RefundFactory.RefundContextEntity> route = route(refundRequestEntity, refundContextEntity);
        if(Objects.isNull( route)){
            return defaulteRefundNode.handle(refundRequestEntity, refundContextEntity);
        }
        return route.handle(refundRequestEntity, refundContextEntity);
    }


    protected void init(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
    }

    public abstract RefundFactory.RefundResponseEntity doHandle(RefundFactory.RefundRequestEntity refundRequestEntity,
                                                     RefundFactory.RefundContextEntity refundContextEntity);

    protected abstract IBaseHandler<RefundFactory.RefundRequestEntity, RefundFactory.RefundResponseEntity, RefundFactory.RefundContextEntity> route(RefundFactory.RefundRequestEntity refundRequestEntity,
                                                                RefundFactory.RefundContextEntity refundContextEntity);
}
