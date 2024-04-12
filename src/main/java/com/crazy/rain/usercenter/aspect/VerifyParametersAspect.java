package com.crazy.rain.usercenter.aspect;

import com.crazy.rain.usercenter.common.ErrorCode;
import com.crazy.rain.usercenter.exception.BasisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @ClassName: VerifyParametersAspect
 * @Description: 校验参数切面
 * @author: CrazyRain
 * @date: 2024/4/2 11:47
 */
@Aspect
@Slf4j
@Component
public class VerifyParametersAspect {

    @Pointcut("execution(* com.crazy.rain.usercenter.*.*.*(..)) && @annotation(com.crazy.rain.usercenter.annotation.VerifyParameters)")
    public void autoFillPointCut() {

    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始参数校验");
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        if (ObjectUtils.isEmpty(args)) {
            return;
        }
        int flag = 0;
        Field[] declaredFields = args[0].getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (isaBoolean(field, args)) {
                    flag++;
                }
            } catch (IllegalAccessException e) {
                log.info("获取参数失败：{}", e.getMessage());
                throw new BasisException(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, "系统内部异常");
            }
        }

        try {
            if (declaredFields.length == flag || (flag == declaredFields.length - 1 && declaredFields[0].get(args[0]) != null)) {
                throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, "请求参数为空");
            }
        } catch (IllegalAccessException e) {
            log.info("获取参数失败：{}", e.getMessage());
            throw new BasisException(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, "系统内部异常");
        }
    }

    private static boolean isaBoolean(Field field, Object[] args) throws IllegalAccessException {
        return !"serialVersionUID".equals(field.getName()) &&
                (field.get(args[0]) == null || StringUtils.isAnyBlank(field.get(args[0]).toString()));
    }
}
