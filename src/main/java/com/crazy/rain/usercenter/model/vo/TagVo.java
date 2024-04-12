package com.crazy.rain.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: TagVo
 * @Description: 标签视图
 * @author: CrazyRain
 * @date: 2024/4/11 下午9:08
 */
@Data
public class TagVo implements Serializable {
    private static final long serialVersionUID = -2932533663119378515L;
    /**
     * 标签id
     */
    private Long id;
    /**
     * 标签名
     */
    private String name;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 父标签Id
     */
    private Long parentId;

    /**
     * 子标签
     */
    private List<TagVo> subTags;
}
