package com.crazy.rain.usercenter.utils;

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
public class RedisUtils {

    public static final String USER_RECOMMENDATIONS = "yupao:user:userRecommendations:";
    public static final String EMAIL_VERIFICATION_CODE = "yupao:user:emailVerificationCode:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> stringObjectsValueOperations;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        stringObjectsValueOperations = redisTemplate.opsForValue();
    }

    public void cacheRecommendation(Long userId, Object object) {
        String format = String.format(USER_RECOMMENDATIONS + "%s", userId);
        stringObjectsValueOperations.set(format, object, 60, TimeUnit.SECONDS);
    }

    public Object getRecommendation(Long userId) {
        String format = String.format(USER_RECOMMENDATIONS + "%s", userId);
        return stringObjectsValueOperations.get(format);
    }

    public void cacheEmailVerificationCode(String email, Object object) {
        String format = String.format(EMAIL_VERIFICATION_CODE + "%s", email);
        stringObjectsValueOperations.set(format, object, 5, TimeUnit.MINUTES);
    }

    public Object getEmailVerificationCode(String email) {
        String format = String.format(EMAIL_VERIFICATION_CODE + "%s", email);
        return stringObjectsValueOperations.get(format);
    }

    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

}
