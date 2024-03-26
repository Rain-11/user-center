package com.crazy.rain.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: BaseResponse
 * @Description: 统一返回类
 * @author: CrazyRain
 * @date: 2024/3/24 20:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private String message;
    private Integer code;
    private T data;
    private String details;

}
