package com.zj.groupbuy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 人群标签和具体人群关联表
 * </p>
 *
 * @author zj
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("crowd_tags_detail")
public class CrowdTagsDetail {

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
     * 用户ID
     */
    private String userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}