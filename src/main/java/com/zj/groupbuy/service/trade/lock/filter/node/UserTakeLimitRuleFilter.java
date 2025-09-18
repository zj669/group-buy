package com.zj.groupbuy.service.trade.lock.filter.node;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.AbstractLogicLink;
import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory.DynamicContext;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleFilterBackEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(2)
public class UserTakeLimitRuleFilter extends AbstractLogicLink<TradeLockRuleCommandEntity,  DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity doApply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext){
        log.info("交易规则过滤-用户参与次数校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());
        GroupBuyActivity groupBuyActivity = dynamicContext.getGroupBuyActivity();

        // 查询用户在一个拼团活动上参与的次数
        Long count = repository.queryOrderCountByActivityId(requestParameter.getActivityId(), requestParameter.getUserId());

        if (null != groupBuyActivity.getTakeLimitCount() && count >= groupBuyActivity.getTakeLimitCount()) {
            log.info("用户参与次数校验，已达可参与上限 activityId:{}, 次数 {}", requestParameter.getActivityId(), count);

            throw new AppException(ResponseCode.E0008.getInfo());
        }

        dynamicContext.setUserTakeOrderCount(count);
        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(count)
                .build();
    }

}