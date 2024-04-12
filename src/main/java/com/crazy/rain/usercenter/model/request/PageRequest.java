package com.crazy.rain.usercenter.model.request;

import lombok.Data;

/**
 * @ClassName: PageRequest
 * @Description: 分页请求父类
 * @author: CrazyRain
 * @date: 2024/4/5 14:27
 */
@Data
public class PageRequest {
    protected Integer pageSize;
    protected Integer currentPage;
}
