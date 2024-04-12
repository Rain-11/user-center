package com.crazy.rain.usercenter.model.dto;

import com.crazy.rain.usercenter.model.request.PageRequest;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: QueryUserByTag
 * @Description: 根据标签搜索用户信息条件类
 * @author: CrazyRain
 * @date: 2024/4/11 上午7:34
 */
@Data
public class QueryUserByTag extends PageRequest {

    private List<String> tags;
}
