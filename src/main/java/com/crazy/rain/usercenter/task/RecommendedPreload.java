package com.crazy.rain.usercenter.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crazy.rain.usercenter.mapper.UserMapper;
import com.crazy.rain.usercenter.model.domain.User;
import com.crazy.rain.usercenter.utils.JsonUtils;
import com.crazy.rain.usercenter.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName: RecommendedPreload
 * @Description: 定时任务推荐预加载
 * @author: CrazyRain
 * @date: 2024/4/3 19:20
 */
@Component
@Slf4j
public class RecommendedPreload {


    private final UserMapper userMapper;
    private final RedisUtils redisUtils;

    private final List<Long> mainUser = List.of(1L);
    private final RedissonClient redissonClient;

    public RecommendedPreload(UserMapper userMapper, RedisUtils redisUtils, RedissonClient redissonClient) {
        this.userMapper = userMapper;
        this.redisUtils = redisUtils;
        this.redissonClient = redissonClient;
    }

    @Scheduled(cron = "0 3 0 * * ? ")
    public void recommendation() {
        RLock lock = redissonClient.getLock("yupao:task:recommendation");
        try {
            if(lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)){
                log.info("定时任务执行");
                LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
                userLambdaQueryWrapper.in(User::getId, mainUser);
                List<User> users = userMapper.selectList(userLambdaQueryWrapper);
                users.forEach(user -> {
                    String userTags = user.getTags();
                    Set<String> tagNames = JsonUtils.getJson(userTags);
                    IPage<User> page = new Page<>(1, 10);
                    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                    for (String name : tagNames) {
                        queryWrapper.like(User::getTags, name).or();
                    }
                    userMapper.selectPage(page, queryWrapper);
                    redisUtils.cacheRecommendation(user.getId(), page.getRecords());
                });
            }
        } catch (InterruptedException e) {
            log.error("Task:recommendation:{}", e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
