package com.crazy.rain.usercenter.model.request;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: ConditionalQueryUser
 * @Description: 分页查询用户信息
 * @author: CrazyRain
 * @date: 2024/3/28 14:57
 */
@Data
public class ConditionalQueryUser {
    /**
     * 当前页
     */
    private Integer current;
    /**
     * 每页显示条数
     */
    private Integer pageSize;

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
     * 性别
     */
    private Integer gender;


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

    /**
     * 创建时间
     */
    private Date createTime;

}
