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
public enum NotifyTaskHTTPEnum implements IBaseEnum<String> {

    SUCCESS("success", "成功"),
    ERROR("error", "失败"),
    NULL(null, "空执行"),
    ;

    @EnumValue
    private String value;
    @JsonValue
    private String label;

}
