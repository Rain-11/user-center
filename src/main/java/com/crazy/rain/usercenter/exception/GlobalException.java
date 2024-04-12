package com.crazy.rain.usercenter.exception;

import com.crazy.rain.usercenter.common.BaseResponse;
import com.crazy.rain.usercenter.common.ErrorCode;
import com.crazy.rain.usercenter.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: GlobalException
 * @Description: 全局异常处理器
 * @author: CrazyRain
 * @date: 2024/3/25 9:20
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {


    @ExceptionHandler(BasisException.class)
    public BaseResponse basisExceptionHandler(BasisException basisException) {
        log.error("自定义异常：", basisException);
        return ResultUtil.error(basisException.getCode(), basisException.getMessage(), basisException.getDetails());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException runtimeException) {
        log.error("运行时异常：", runtimeException);
        return ResultUtil.error(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, "系统内部异常");
    }

}
