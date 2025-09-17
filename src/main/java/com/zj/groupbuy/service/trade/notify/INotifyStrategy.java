package com.zj.groupbuy.service.trade.notify;

import com.zj.groupbuy.model.entity.NotifyTask;
import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;

import java.io.IOException;

public interface INotifyStrategy{
    String notify(NotifyTask notifyTask) throws Exception;
    NotifyTaskMethodenum  getNotifyMethod();
}
