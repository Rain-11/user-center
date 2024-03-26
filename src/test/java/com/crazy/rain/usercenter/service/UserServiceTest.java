package com.crazy.rain.usercenter.service;

import com.crazy.rain.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void insert() {
        User user = new User();
        user.setUsername("测试添加");
        user.setUserPassword("132456");
        userService.save(user);
    }

    @Test
    void registrationVerification() {

    }
}