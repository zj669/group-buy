package com.zj.groupbuy.service.trade.refund.refundtree.facotory;

import com.zj.groupbuy.model.entity.GroupBuyOrder;
import com.zj.groupbuy.model.entity.GroupBuyOrderList;
import com.zj.groupbuy.service.trade.refund.refundtree.node.RootNode;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RefundFactory {
    @Resource
    private RootNode refundRootNode;

    public RefundResponseEntity refund(RefundRequestEntity refundRequestEntity,  RefundContextEntity refundContextEntity) {
        return refundRootNode.handle(refundRequestEntity, refundContextEntity);
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class RefundRequestEntity {
        /** 预购订单ID */
        private String orderId;
        /** 用户id */
        private String userId;
        /** 拼团id **/
        private String teamId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class RefundResponseEntity {
        /** 预购订单ID */
        private String orderId;
        /** 用户id */
        private String userId;
        /** 拼团id **/
        private String teamId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class RefundContextEntity {

        private GroupBuyOrder team;

        private GroupBuyOrderList order;

    }


}
