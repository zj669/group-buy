package com.zj.groupbuy.service.trade.notify.impl;

import com.zj.groupbuy.model.entity.NotifyTask;
import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;
import com.zj.groupbuy.service.trade.notify.INotifyStrategy;
import org.springframework.stereotype.Component;

@Component
public class MQNotifyStrategey implements INotifyStrategy {
    @Override
    public String notify(NotifyTask notifyTask) {
        return "";
    }

    @Override
    public NotifyTaskMethodenum getNotifyMethod() {
        return NotifyTaskMethodenum.MQ;
    }
}
