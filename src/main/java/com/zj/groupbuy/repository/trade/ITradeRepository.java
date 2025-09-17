package com.zj.groupbuy.repository.trade;

import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.service.trade.model.aggregate.lock.GroupBuyOrderAggregate;
import com.zj.groupbuy.service.trade.model.aggregate.settlement.GroupBuyTeamSettlementAggregate;
import com.zj.groupbuy.service.trade.model.entity.lock.GroupBuyProgressVO;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.GroupBuyTeamEntity;

public interface ITradeRepository {
    GroupBuyActivity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Long queryOrderCountByActivityId(Long activityId, String userId);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity queryNoPayMarketPayOutOrder(String userId, String outTradeNo);

    MarketPayOrderEntity lockPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    boolean isSCBlackIntercept(String source, String channel);

    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId);

    void settlementMarketPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);
}
