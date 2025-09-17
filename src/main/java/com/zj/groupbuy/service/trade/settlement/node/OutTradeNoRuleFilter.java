package com.zj.groupbuy.service.trade.settlement.node;


import com.zj.groupbuy.common.design.singletonresponsibilitychain.AbstractLogicLink;
import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.model.entity.lock.MarketPayOrderEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.settlement.TradeSettlementRuleFilterBackEntity;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory;
import com.zj.groupbuy.service.trade.settlement.factory.TradeSettlementRuleFilterFactory.DynamicContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@Order(2)
public class OutTradeNoRuleFilter extends AbstractLogicLink<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> {
    @Resource
    private ITradeRepository repository;
    @Override
    public TradeSettlementRuleFilterBackEntity doApply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) {
        log.info("结算规则过滤-外部单号校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());


        // 查询拼团信息
        MarketPayOrderEntity marketPayOrderEntity = repository.queryMarketPayOrderEntityByOutTradeNo(requestParameter.getUserId(), requestParameter.getOutTradeNo());

        if (null == marketPayOrderEntity) {
            log.error("不存在的外部交易单号或用户已退单，不需要做支付订单结算:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
            throw new AppException(ResponseCode.E0104);
        }

        dynamicContext.setMarketPayOrderEntity(marketPayOrderEntity);
        return TradeSettlementRuleFilterBackEntity.builder().build();
    }
}
