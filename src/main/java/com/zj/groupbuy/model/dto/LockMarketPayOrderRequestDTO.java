package com.zj.groupbuy.model.dto;

import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;
import lombok.Data;

@Data
public class LockMarketPayOrderRequestDTO {

    // 用户ID
    private String userId;
    // 拼单组队ID - 可为空，为空则创建新组队ID
    private String teamId;
    // 活动ID
    private Long activityId;
    // 商品ID
    private String goodsId;
    // 渠道
    private String source;
    // 来源
    private String channel;
    // 外部交易单号
    private String outTradeNo;
    // 回调地址
    private String notify;
    private NotifyTaskMethodenum notifyType;

}