package com.crazy.rain.usercenter.controller;

import com.crazy.rain.usercenter.common.BaseResponse;
import com.crazy.rain.usercenter.common.ResultUtil;
import com.crazy.rain.usercenter.model.vo.TagVo;
import com.crazy.rain.usercenter.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: TagController
 * @Description: 标签接口
 * @author: CrazyRain
 * @date: 2024/4/11 下午9:07
 */
@RestController
@Slf4j
@RequestMapping("/tag")
@AllArgsConstructor
@Tag(name = "标签接口")
public class TagController {
    private final TagService tagService;

    @GetMapping("/getTags")
    @Operation(summary = "获取全部标签")
    public BaseResponse<List<TagVo>> getTags() {
        return ResultUtil.success(tagService.getTags());
    }

}
