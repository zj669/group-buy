package com.zj.groupbuy.service.trade;

import com.zj.groupbuy.service.trade.model.entity.lock.PayActivityEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.PayDiscountEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.UserEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.GroupBuyProgressVO;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;

public interface ITradeLockService {

    MarketPayOrderEntity queryNoPayMarketPayOutOrder(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity);
}