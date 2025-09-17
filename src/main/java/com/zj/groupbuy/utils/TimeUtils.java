package com.zj.groupbuy.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 使用系统默认时区转换
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, ZoneId.systemDefault());
    }

    /**
     * 使用指定时区转换
     * @param zoneId 时区ID (如 ZoneId.of("Asia/Shanghai"))
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
        Objects.requireNonNull(localDateTime, "LocalDateTime不能为空");
        Objects.requireNonNull(zoneId, "时区不能为空");
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * 使用Java 8 API给Date对象添加指定分钟数
     * @param date 原始Date对象
     * @param minutes 要添加的分钟数
     * @return 添加指定分钟数后的新Date对象
     */
    public static Date addMinutesToDate(Date date, int minutes) {
        // 转换为LocalDateTime进行操作
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .plusMinutes(minutes);

        // 转换回Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}