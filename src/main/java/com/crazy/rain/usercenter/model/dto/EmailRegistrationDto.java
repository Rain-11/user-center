package com.crazy.rain.usercenter.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: EmailRegistrationDto
 * @Description: 邮箱注册参数
 * @author: CrazyRain
 * @date: 2024/4/15 下午7:10
 */
@Data
public class EmailRegistrationDto implements Serializable {

    private static final long serialVersionUID = -1480481329332669459L;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 校验码
     */
    private String code;

}
