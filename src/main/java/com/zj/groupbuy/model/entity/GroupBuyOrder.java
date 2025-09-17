package com.zj.groupbuy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.groupbuy.model.enums.GroupBuyOrderEnum;
import com.zj.groupbuy.model.enums.NotifyTaskMethodenum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("group_buy_order")
public class GroupBuyOrder {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 拼单组队ID
     */
    private String teamId;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 渠道
     */
    private String source;

    /**
     * 来源
     */
    private String channel;

    /**
     * 原始价格
     */
    private BigDecimal originalPrice;

    /**
     * 折扣金额
     */
    private BigDecimal deductionPrice;

    /**
     * 支付价格
     */
    private BigDecimal payPrice;

    /**
     * 目标数量
     */
    private Integer targetCount;

    /**
     * 完成数量
     */
    private Integer completeCount;

    /**
     * 锁单数量
     */
    private Integer lockCount;

    /**
     * 状态（0-拼单中、1-完成、2-失败、3-完成-含退单）
     */
    private GroupBuyOrderEnum status;

    /**
     * 拼团开始时间
     */
    private Date validStartTime;

    /**
     * 拼团结束时间
     */
    private Date validEndTime;

    /**
     * 回调类型（HTTP、MQ）
     */
    private NotifyTaskMethodenum notifyType;

    /**
     * 回调地址（HTTP 回调不可为空）
     */
    private String notifyUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}