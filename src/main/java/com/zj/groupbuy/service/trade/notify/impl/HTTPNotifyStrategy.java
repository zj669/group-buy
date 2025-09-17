package com.zj.groupbuy.service.trade.notify.impl;

import com.zj.groupbuy.model.entity.NotifyTask;
import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;
import com.zj.groupbuy.service.trade.notify.INotifyStrategy;
import com.zj.groupbuy.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

@Component
@Slf4j
public class HTTPNotifyStrategy implements INotifyStrategy {
    @Override
    public String notify(NotifyTask notifyTask) throws Exception{
        String notifyUrl = notifyTask.getNotifyUrl();
        String parameterJson = notifyTask.getParameterJson();
        if(notifyUrl == null){
            log.error("notifyUrl is null");
            return "";
        }
        HttpResponse<String> post = HttpClientUtil.post(notifyUrl, parameterJson);
        return post.body();
    }

    @Override
    public NotifyTaskMethodenum getNotifyMethod() {
        return NotifyTaskMethodenum.HTTP;
    }
}
