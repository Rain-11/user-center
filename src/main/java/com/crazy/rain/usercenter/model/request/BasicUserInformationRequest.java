package com.crazy.rain.usercenter.model.request;

import lombok.Data;

/**
 * @ClassName: BasicUserInformationRequest
 * @Description: 设置用户基础信息DTO
 * @author: CrazyRain
 * @date: 2024/3/27 20:36
 */
@Data
public class BasicUserInformationRequest {
    private Long id;
    private String username;
    private String phone;
    private Integer gender;
    private String email;
    private String avatarUrl;
}
