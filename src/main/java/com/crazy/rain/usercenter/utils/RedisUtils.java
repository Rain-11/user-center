package com.crazy.rain.usercenter.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtils
 * @Description: 缓存工具类
 * @author: CrazyRain
 * @date: 2024/4/4 14:35
 */
@Component
@AllArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;


    public void cacheRecommendation(Long userId, Object object) {
        ValueOperations<String, Object> stringObjectsValueOperations = redisTemplate.opsForValue();
        String format = String.format("yupao:user:userRecommendations:%s", userId);
        stringObjectsValueOperations.set(format, object, 60, TimeUnit.SECONDS);
    }

    public Object getRecommendation(Long userId) {
        ValueOperations<String, Object> stringObjectsValueOperations = redisTemplate.opsForValue();
        String format = String.format("yupao:user:userRecommendations:%s", userId);
        return stringObjectsValueOperations.get(format);
    }

}
