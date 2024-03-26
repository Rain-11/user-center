package com.crazy.rain.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: UserRegisterRequest
 * @Description: 用户注册参数类
 * @author: CrazyRain
 * @date: 2024/3/23 20:09
 */
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -8734460205069457532L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;

    private String planetCode;
}
