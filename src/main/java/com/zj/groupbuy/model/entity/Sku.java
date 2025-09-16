package com.zj.groupbuy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sku")
public class Sku {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 渠道
     */
    private String source;

    /**
     * 来源
     */
    private String channel;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品价格
     */
    private BigDecimal originalPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}