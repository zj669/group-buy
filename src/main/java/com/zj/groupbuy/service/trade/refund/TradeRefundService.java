package com.zj.groupbuy.service.trade.refund;

import com.zj.groupbuy.service.trade.ITradeRefundService;
import com.zj.groupbuy.service.trade.refund.refundtree.facotory.RefundFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TradeRefundService implements ITradeRefundService {
    @Resource
    private RefundFactory refundFactory;

    @Override
    public Boolean refund(String teamId, String orderId) {
        RefundFactory.RefundRequestEntity refundRequestEntity = RefundFactory.RefundRequestEntity.builder()
                .teamId(teamId)
                .orderId(orderId)
                .build();
        RefundFactory.RefundContextEntity refundContextEntity = RefundFactory.RefundContextEntity.builder()
                .build();
        RefundFactory.RefundResponseEntity refundResponseEntity = refundFactory.refund(refundRequestEntity, refundContextEntity);
        return Boolean.TRUE;
    }
}
