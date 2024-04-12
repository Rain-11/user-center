package com.crazy.rain.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @ClassName: RedisConfig
 * @Description: redis配置
 * @author: CrazyRain
 * @date: 2024/4/3 15:14
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> stringObjectRedisTemplate = new RedisTemplate<>();
        stringObjectRedisTemplate.setConnectionFactory(connectionFactory);
        stringObjectRedisTemplate.setKeySerializer(RedisSerializer.string());
        return stringObjectRedisTemplate;
    }
}
