package com.zj.groupbuy.service.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketProductEntity {

    /** 活动ID */
    private Long activityId;
    /** 用户ID */
    private String userId;
    /** 商品ID */
    private String goodsId;
    /** 来源 */
    private String source;
    /** 渠道 */
    private String channel;

}