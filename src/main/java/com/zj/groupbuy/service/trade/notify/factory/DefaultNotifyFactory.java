package com.zj.groupbuy.service.trade.notify.factory;

import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;
import com.zj.groupbuy.service.trade.notify.INotifyStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DefaultNotifyFactory {
    @Resource
    private List<INotifyStrategy> notifyStrategyList;

    private Map<NotifyTaskMethodenum, INotifyStrategy> notifyStrategyMap;

    @PostConstruct
    public void init() {
        notifyStrategyMap = notifyStrategyList.stream().collect(
                java.util.stream.Collectors.toMap(INotifyStrategy::getNotifyMethod, v -> v));
    }

    public INotifyStrategy getNotifyStrategy(NotifyTaskMethodenum notifyMethod) {
        return notifyStrategyMap.get(notifyMethod);
    }
}
