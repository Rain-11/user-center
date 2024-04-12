package com.crazy.rain.usercenter.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: CreateTeamDto
 * @Description: 创建队伍Dto
 * @author: CrazyRain
 * @date: 2024/4/5 13:46
 */
@Data
public class TeamDto {

    /**
     * 队伍id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String teamName;

    /**
     * 队伍描述
     */
    private String details;

    /**
     * 最大人数默认为5
     */
    private Integer maximumNum;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 队伍状态（0公开、1私有、2加密）默认为0
     */
    private Integer status;

    /**
     * 队伍密码
     */
    private String password;


}
