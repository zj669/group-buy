package com.zj.groupbuy.service.trade.lock.filter.node;

import com.zj.groupbuy.common.design.singletonresponsibilitychain.AbstractLogicLink;
import com.zj.groupbuy.exception.AppException;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.enums.ActivityStatusEnum;
import com.zj.groupbuy.model.enums.ResponseCode;
import com.zj.groupbuy.repository.trade.ITradeRepository;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory;
import com.zj.groupbuy.service.trade.lock.filter.factory.TradeLockRuleFilterFactory.DynamicContext;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleCommandEntity;
import com.zj.groupbuy.service.trade.model.entity.lock.TradeLockRuleFilterBackEntity;
import com.zj.groupbuy.utils.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@Order(1)
public class ActivityUsabilityRuleFilter extends AbstractLogicLink<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext){
        log.info("交易规则过滤1-活动的可用性校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        // 查询拼团活动
        GroupBuyActivity groupBuyActivity = repository.queryGroupBuyActivityEntityByActivityId(requestParameter.getActivityId());

        // 校验；活动状态 - 可以抛业务异常code，或者把code写入到动态上下文dynamicContext中，最后获取。
        if (!ActivityStatusEnum.EFFECTIVE.equals(groupBuyActivity.getStatus())) {
            log.info("活动的可用性校验，非生效状态 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0007.getInfo());
        }

        // 校验；活动时间
        Date currentTime = new Date();
        if (currentTime.before(groupBuyActivity.getStartTime()) || currentTime.after(groupBuyActivity.getEndTime())) {
            log.info("活动的可用性校验，非可参与时间范围 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0007.getInfo());
        }

        // 写入动态上下文
        dynamicContext.setGroupBuyActivity(groupBuyActivity);

        return TradeLockRuleFilterBackEntity.builder().build();
    }

}