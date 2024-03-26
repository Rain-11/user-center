package com.crazy.rain.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: UserLoginRequest
 * @Description: 用户登录参数
 * @author: CrazyRain
 * @date: 2024/3/23 20:26
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 6183003085198398636L;

    private String userAccount;
    private String userPassword;


}
