package com.crazy.rain.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: PageVo
 * @Description: 分页视图
 * @author: CrazyRain
 * @date: 2024/4/8 上午9:57
 */
@Data
public class PageVo<T> implements Serializable {
    private static final long serialVersionUID = 487817486357051225L;
    private Long total;
    private T records;
    private Long pages;
}
