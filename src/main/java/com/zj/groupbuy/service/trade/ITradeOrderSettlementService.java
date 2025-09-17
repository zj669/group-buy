package com.zj.groupbuy.service.trade;

import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySettlementEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;

public interface ITradeOrderSettlementService {

    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity);
}
