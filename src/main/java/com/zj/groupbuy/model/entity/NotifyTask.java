package com.zj.groupbuy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("notify_task")
public class NotifyTask {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 拼单组队ID
     */
    private String teamId;

    /**
     * 回调种类
     */
    private String notifyCategory;

    /**
     * 回调类型（HTTP、MQ）
     */
    private NotifyTaskMethodenum notifyType;

    /**
     * 回调消息
     */
    private String notifyMq;

    /**
     * 回调接口
     */
    private String notifyUrl;

    /**
     * 回调次数
     */
    private Integer notifyCount;

    /**
     * 回调状态【0初始、1完成、2重试、3失败】
     */
    private Integer notifyStatus;

    /**
     * 参数对象
     */
    private String parameterJson;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}