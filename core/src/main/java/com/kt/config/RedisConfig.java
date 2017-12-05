package com.kt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisConfig
 */
@Configuration
@PropertySource("classpath:config.properties")
public class RedisConfig {
    @Value("${redis.hostName}")
    private String redisHostName;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHostName);
        jedisConnectionFactory.setPort(Integer.parseInt(redisPort));
        jedisConnectionFactory.setPassword(redisPassword);
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.afterPropertiesSet();
        return new StringRedisTemplate(jedisConnectionFactory);
    }
}
