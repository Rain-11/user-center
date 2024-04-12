package com.crazy.rain.usercenter.exception;

import com.crazy.rain.usercenter.common.ErrorCode;
import lombok.Getter;

/**
 * @ClassName: BasisException
 * @Description: 自定义异常
 * @author: CrazyRain
 * @date: 2024/3/25 9:18
 */
@Getter
public class BasisException extends RuntimeException {
    private final Integer code;
    private final String message;
    private final String details;

    public BasisException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.details = details;
        this.message = errorCode.getMessage();
    }

    public BasisException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.details = "";
        this.message = errorCode.getMessage();
    }


}
