package com.crazy.rain.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: UserVo
 * @Description: 用户视图
 * @author: CrazyRain
 * @date: 2024/4/6 9:37
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -4601571956600474291L;
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0 正常
     */
    private Integer userStatus;

    /**
     * 0默认用户，1管理员
     */
    private Integer userRole;


    private String planetCode;

    private String tags;

}
