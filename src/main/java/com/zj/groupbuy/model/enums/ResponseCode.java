package com.zj.groupbuy.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),

    E0003("003","活动降级"),
    E0004("004","人群切量，只能一定范围的用户参加"),
    E0006("0006", "拼团人数已经满"),
    E0007("0007", "活动参与非法"),
    E0008("008","用户已达上限"),
    E0010("0010","拼团已经达到上线"),
    E0015("", ""),
    E0104("", ""),
    E0106("", "未在有效期"),
    UPDATE_ZERO("", "");

    private String code;
    private String info;

}