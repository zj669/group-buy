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
     * 使用UTC时区转换
     */
    public static Date toUtcDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, ZoneOffset.UTC);
    }

    /**
     * 安全转换（处理null值）
     * @return 如果输入为null则返回null
     */
    public static Date toDateSafely(LocalDateTime localDateTime) {
        return localDateTime != null ? toDate(localDateTime) : null;
    }
}