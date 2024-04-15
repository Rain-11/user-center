package com.crazy.rain.usercenter.utils;

/**
 * @ClassName: RandomNumberUtils
 * @Description: 获取随机数工具类
 * @author: CrazyRain
 * @date: 2024/4/14 下午9:45
 */
public class RandomNumberUtils {
    public static int obtainVerificationCode() {
        return (int) (Math.random() * 100000);
    }
}
