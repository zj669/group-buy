package com.zj.groupbuy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 人群标签表
 * </p>
 *
 * @author zj
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("crowd_tags")
public class CrowdTags {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 人群ID
     */
    private String tagId;

    /**
     * 人群名称
     */
    private String tagName;

    /**
     * 人群描述
     */
    private String tagDesc;

    /**
     * 人群标签统计量
     */
    private Integer statistics;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}