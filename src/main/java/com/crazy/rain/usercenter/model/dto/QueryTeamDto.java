package com.crazy.rain.usercenter.model.dto;

import com.crazy.rain.usercenter.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ClassName: CreateTeamDto
 * @Description: 创建队伍Dto
 * @author: CrazyRain
 * @date: 2024/4/5 13:46
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class QueryTeamDto extends PageRequest {

    /**
     * 队伍id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 关键字
     */
    private String searchKey;

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


}
