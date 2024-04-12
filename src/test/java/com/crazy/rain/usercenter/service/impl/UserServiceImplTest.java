package com.crazy.rain.usercenter.service.impl;

import com.crazy.rain.usercenter.service.UserService;
import com.crazy.rain.usercenter.utils.AlgorithmUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    private UserService userService;


    @Test
    void testQueryUsersBasedOnTags() {
        List<String> te1 = List.of("python","c++","男");
        List<String> te2 = List.of("java","c++","男");
        List<String> te3 = List.of("java","c++","女");
        int i = AlgorithmUtils.minDistance(te1, te2);
        int i1 = AlgorithmUtils.minDistance(te1, te3);
        System.out.println(i);
        System.out.println("==============");
        System.out.println(i1);

    }
}