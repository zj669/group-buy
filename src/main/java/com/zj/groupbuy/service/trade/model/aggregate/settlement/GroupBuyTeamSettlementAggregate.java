package com.zj.groupbuy.service.trade.model.aggregate.settlement;

import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamSettlementAggregate {

    /** 用户实体对象 */
    private UserEntity userEntity;
    /** 拼团组队实体对象 */
    private GroupBuyTeamEntity groupBuyTeamEntity;
    /** 交易支付订单实体对象 */
    private TradePaySuccessEntity tradePaySuccessEntity;

}