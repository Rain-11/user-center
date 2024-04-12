package com.crazy.rain.usercenter.service;

import com.crazy.rain.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    @Test
    void insert() {
        User user = new User();
        user.setUsername("测试添加");
        user.setUserPassword("132456");
        userService.save(user);
    }

    @Test
    void registrationVerification() {
        RLock lock = redissonClient.getLock("yupao:task:recommendation");
        try {
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                Thread.sleep(300000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}