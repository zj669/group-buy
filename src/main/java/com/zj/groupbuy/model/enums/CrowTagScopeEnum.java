package com.zj.groupbuy.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.zj.groupbuy.common.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 人群标签规则
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CrowTagScopeEnum  implements IBaseEnum<Integer> {

    NOTVISIBLE(1, "不可见"),
    NOTSIGNUP(2, "不可参加"),
    ;

    @EnumValue
    private Integer value;
    @JsonValue
    private String label;


}