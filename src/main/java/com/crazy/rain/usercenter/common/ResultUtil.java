package com.crazy.rain.usercenter.common;

/**
 * @ClassName: ResultUtil
 * @Description: 返回值工具类
 * @author: CrazyRain
 * @date: 2024/3/24 20:32
 */

public class ResultUtil {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("", 0, data, "");
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(message, 0, data, "");
    }

    public static <T> BaseResponse<T> success(Integer code, String message, T data) {
        return new BaseResponse<>(message, code, data, "");
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String details) {
        return new BaseResponse<>(errorCode.getMessage(), errorCode.getCode(), null, details);
    }

    public static <T> BaseResponse<T> error(Integer code, String message, String details) {
        return new BaseResponse<>(message, code, null, details);
    }

}
