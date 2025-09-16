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
public enum GroupBuyOrderEnum implements IBaseEnum<Integer> {

    PROGRESS(0, "拼单中"),
    COMPLETE(1, "完成"),
    FAIL(2, "失败"),
    COMPLETE_FAIL(3, "完成-含退单"),
    ;

    @EnumValue
    private Integer value;
    @JsonValue
    private String label;


}
