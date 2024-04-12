package com.crazy.rain.usercenter.common;

import lombok.Getter;

/**
 * 全局返回信息类
 *
 * @author CrazyRain
 */

@Getter
public enum ErrorCode {

    /**
     * 全局状态信息
     */
    SUCCESS(0, "ok"),
    PARAMETER_DOES_NOT_EXIST(40001, "请求参数不存在"),
    REQUEST_PARAMETERS_ERROR(40002, "请求参数错误"),
    NOT_LOGGED_IN(40100, "未登录"),
    NO_PERMISSION(40101, "无权限"),

    INTERNAL_SYSTEM_EXCEPTION(50000, "系统内部异常");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
