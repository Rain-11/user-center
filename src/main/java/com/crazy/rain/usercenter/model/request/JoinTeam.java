package com.crazy.rain.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: JoinTeam
 * @Description: 加入队伍参数
 * @author: CrazyRain
 * @date: 2024/4/6 下午3:14
 */
@Data
public class JoinTeam  implements Serializable {
    private static final long serialVersionUID = 497857619954774036L;
    /**
     * 队伍id
     */
    private Long teamId;


    /**
     * 队伍密码
     */
    private String password;
}
