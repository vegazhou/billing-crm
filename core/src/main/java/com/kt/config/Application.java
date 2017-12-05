package com.kt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Application
 */
@ComponentScan(basePackages = {
        "com.kt.repo",
        "com.kt.service",
        "com.kt.biz",
        "com.kt.sys",
        "wang.huaichao.conf",
        "wang.huaichao.data",
})
@Configuration
@PropertySource("classpath:config.properties")
public class Application extends WebMvcConfigurerAdapter {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
