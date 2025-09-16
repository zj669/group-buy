package com.zj.groupbuy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 数据源配置
 * 配置主库(MyBatis-Plus)和ModuleBase(JPA)两个数据源
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
}
@Configuration
@MapperScan(
        basePackages = "com.zj.groupbuy.repository.mapper"
)
class MybatisMapperScanConfig {
}