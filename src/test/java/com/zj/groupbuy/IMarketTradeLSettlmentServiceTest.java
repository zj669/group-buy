package com.zj.groupbuy;


import com.alibaba.fastjson2.JSON;
import com.zj.groupbuy.common.design.singletonresponsibilitychain.ILogicLink;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySettlementEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradePaySuccessEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.settlement.TradeOrderSettlementService;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory;
import com.zj.groupbuy.utils.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@Slf4j

@SpringBootTest
public class IMarketTradeLSettlmentServiceTest {
    @Resource
    private TradeOrderSettlementService marketTradeLSettlmentService;
    @Resource
    private TradeSettlementRuleFilterFactory tradeSettlmentRuleFilterFactory;

    @Test
    public void test_settlement() {
        TradePaySettlementEntity tradePaySettlementEntity = marketTradeLSettlmentService.settlementMarketPayOrder(TradePaySuccessEntity.builder()
                .outTradeNo("871591311565")
                .userId("zj01")
                .outTradeTime(new Date(System.currentTimeMillis()))
                .source("s01")
                .channel("c01")
                .build());

        System.out.println(JSON.toJSONString(tradePaySettlementEntity));
    }

    @Test
    public void test_settlementMarketPayOrder() {
        Date begin = new Date(System.currentTimeMillis());
        Date end = TimeUtils.addMinutesToDate(begin, 3);
        System.out.println(JSON.toJSONString(begin));
        System.out.println(JSON.toJSONString(end));
    }

}
