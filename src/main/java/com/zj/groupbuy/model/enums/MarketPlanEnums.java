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
public enum MarketPlanEnums implements IBaseEnum<String> {
    ZJ("ZJ", "直减优惠"),
    MJ("MJ", "满减优惠"),
    N("N", "N元购优惠"),
    ZK("ZK", "折扣优惠"),
    ;

    @EnumValue
    private String value;
    @JsonValue
    private String label;
}