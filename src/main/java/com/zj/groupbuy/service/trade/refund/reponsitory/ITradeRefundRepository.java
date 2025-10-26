package com.zj.groupbuy.service.trade.refund.reponsitory;

import com.zj.groupbuy.model.entity.GroupBuyOrderList;

import java.util.List;

public interface ITradeRefundRepository {
    List<GroupBuyOrderList> selectByOutTimeAndNotPay();
}
