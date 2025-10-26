package com.zj.groupbuy.integretion.task;

import com.alibaba.fastjson2.JSON;
import com.zj.groupbuy.model.entity.GroupBuyOrderList;
import com.zj.groupbuy.service.trade.ITradeOrderSettlementService;
import com.zj.groupbuy.service.trade.ITradeRefundService;
import com.zj.groupbuy.service.trade.refund.reponsitory.ITradeRefundRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GroupBuyRefundJob {

    @Resource
    private ITradeRefundRepository tradeRefundRepository;
    @Resource
    private ITradeRefundService tradeRefundService;

    @Scheduled(cron = "0/15 * * * * ?")
    public void exec() {
        try {
            List<GroupBuyOrderList> groupBuyOrderLists = tradeRefundRepository.selectByOutTimeAndNotPay();
            groupBuyOrderLists.forEach(groupBuyOrderList -> {
                tradeRefundService.refund(groupBuyOrderList.getTeamId(), groupBuyOrderList.getOrderId());
            });
            log.info("定时任务，除去超时未支付的订单");
        } catch (Exception e) {
            log.error("定时任务，除去超时未支付的订单", e);
        }
    }

}