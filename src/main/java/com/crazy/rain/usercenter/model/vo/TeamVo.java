package com.crazy.rain.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: TeamVo
 * @Description: 队伍查询视图
 * @author: CrazyRain
 * @date: 2024/4/6 9:23
 */
@Data
public class TeamVo implements Serializable {
    private static final long serialVersionUID = 5026438402112981096L;
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
     * 创建人id
     */
    private Long userId;

    /**
     * 队伍状态（0公开、1私有、2加密）默认为0
     */
    private Integer status;

    /**
     * 创建人信息
     */
    private UserVo captainInformation;

    /**
     * 队伍成员信息
     */
    private List<UserVo> memberInformation;
}
