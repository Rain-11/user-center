package com.crazy.rain.usercenter.service;

import com.crazy.rain.usercenter.model.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crazy.rain.usercenter.model.vo.TagVo;

import java.util.List;

/**
* @author CrazyRain
* @description 针对表【tag】的数据库操作Service
* @createDate 2024-04-11 21:03:55
*/
public interface TagService extends IService<Tag> {

    /**
     * 获取全部标签
     * @return 标签集合
     */
    List<TagVo> getTags();
}
