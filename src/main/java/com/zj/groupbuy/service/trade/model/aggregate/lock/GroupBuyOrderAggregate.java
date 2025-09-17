package com.zj.groupbuy.service.trade.model.aggregate.lock;

import com.zj.groupbuy.service.trade.model.entity.lock.PayActivityEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.PayDiscountEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderAggregate {

    /** 用户实体对象 */
    private UserEntity userEntity;
    /** 支付活动实体对象 */
    private PayActivityEntity payActivityEntity;
    /** 支付优惠实体对象 */
    private PayDiscountEntity payDiscountEntity;

    private Long userTakeOrderCount;

}