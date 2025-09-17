package com.zj.groupbuy.service.trade;

import com.zj.groupbuy.model.entity.NotifyTask;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySettlementEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;

import java.util.List;
import java.util.Map;

public interface ITradeOrderSettlementService {

    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity);
    Map<String, Integer> execSettlementNotifyJob();
    Map<String, Integer> execSettlementNotifyJob(String teamId);
    Map<String, Integer> execSettlementNotifyJob(List<NotifyTask> notifyTaskEntityList);
}
