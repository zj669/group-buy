package com.zj.groupbuy.service.trade.refund.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRefundEntity {
    /** 预购订单ID */
    private String orderId;
    /** 用户id */
    private String userId;
    /** 拼团id **/
    private String teamId;

}
