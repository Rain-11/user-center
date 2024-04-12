package com.crazy.rain.usercenter.constant;

public interface UserConstant {
    /**
     * 用户登录状态
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户权限
     */
    int ADMIN_USER = 1;
    int DEFAULT_USER = 0;

    String REQUEST_PARAMETER_IS_EMPTY = "请求参数为空";
    String USER_DOES_NOT_HAVE_PERMISSION = "请求参数为空";
    String PASSWORD_LENGTH_ABNORMALITY = "密码长度小于8";
}
