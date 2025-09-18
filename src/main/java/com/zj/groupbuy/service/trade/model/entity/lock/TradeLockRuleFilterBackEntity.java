package com.zj.groupbuy.service.trade.model.entity.lock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeLockRuleFilterBackEntity {

    // 用户参与活动的订单量
    private Long userTakeOrderCount;

    // 恢复组队库存缓存key
    private String recoveryTeamStockKey;

}