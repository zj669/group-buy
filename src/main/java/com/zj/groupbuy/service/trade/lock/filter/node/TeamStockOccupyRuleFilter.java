package com.zj.groupbuy.service.trade.lock.filter.node;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.AbstractLogicLink;
import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleFilterBackEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@Slf4j
public class TeamStockOccupyRuleFilter extends AbstractLogicLink<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {
    @Resource
    private ITradeRepository repository;

    @Override
    protected TradeLockRuleFilterBackEntity doApply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) {
        log.info("交易规则过滤-组队库存校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        // 1. teamId 为空，则为首次开团，不做拼团组队目标量库存限制
        String teamId = requestParameter.getTeamId();
        if (StringUtils.isBlank(teamId)) {
            return TradeLockRuleFilterBackEntity.builder()
                    .userTakeOrderCount(dynamicContext.getUserTakeOrderCount())
                    .build();
        }

        // 2. 抢占库存；通过抢占 Redis 缓存库存，来降低对数据库的操作压力。
        GroupBuyActivity groupBuyActivity = dynamicContext.getGroupBuyActivity();
        Integer target = groupBuyActivity.getTarget();
        Integer validTime = groupBuyActivity.getValidTime();
        String teamStockKey = dynamicContext.generateTeamStockKey(teamId);
        String recoveryTeamStockKey = dynamicContext.generateRecoveryTeamStockKey(teamId);

        boolean status = repository.occupyTeamStock(teamStockKey, recoveryTeamStockKey, target, validTime);

        if (!status) {
            log.warn("交易规则过滤-组队库存校验{} activityId:{} 抢占失败:{}", requestParameter.getUserId(), requestParameter.getActivityId(), teamStockKey);
            throw new AppException(ResponseCode.E0008);
        }

        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(dynamicContext.getUserTakeOrderCount())
                .recoveryTeamStockKey(recoveryTeamStockKey)
                .build();
    }
    }

