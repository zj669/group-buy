package com.zj.groupbuy.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.zj.groupbuy.common.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NotifyTaskMethodenum implements IBaseEnum<String> {
    MQ("mq", "消息队列"),
    HTTP("http", "发送请求"),
    ;

    @EnumValue
    private String value;
    @JsonValue
    private String label;
}
