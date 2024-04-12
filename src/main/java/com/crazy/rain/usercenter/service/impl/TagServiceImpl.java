package com.crazy.rain.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crazy.rain.usercenter.model.domain.Tag;
import com.crazy.rain.usercenter.model.vo.TagVo;
import com.crazy.rain.usercenter.service.TagService;
import com.crazy.rain.usercenter.mapper.TagMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author CrazyRain
 * @description 针对表【tag】的数据库操作Service实现
 * @createDate 2024-04-11 21:03:55
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Override
    public List<TagVo> getTags() {
        List<Tag> tags = this.list();
        List<TagVo> firstLevelLabel = tags.stream()
                .filter(tag -> tag.getParentId().equals(0L))
                .map(tag -> getTagVo(tag))
                .collect(Collectors.toList());
        Map<Long, List<TagVo>> secondaryLabel = tags.stream()
                .filter(tag -> !tag.getParentId().equals(0L))
                .map(tag -> getTagVo(tag))
                .collect(Collectors.groupingBy(TagVo::getParentId));
        firstLevelLabel.forEach(tagVo -> {
            tagVo.setSubTags(secondaryLabel.get(tagVo.getId()));
        });
        return firstLevelLabel;
    }

    private static TagVo getTagVo(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
}




