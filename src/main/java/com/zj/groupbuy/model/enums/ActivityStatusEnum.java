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
public enum ActivityStatusEnum implements IBaseEnum<Integer> {

    CREATE(0, "创建"),
    EFFECTIVE(1, "生效"),
    OVERDUE(2, "过期"),
    ABANDONED(3, "废弃"),
    ;
    @EnumValue
    private Integer value;
    @JsonValue
    private String label;


}
