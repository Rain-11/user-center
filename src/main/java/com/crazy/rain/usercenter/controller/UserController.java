package com.crazy.rain.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crazy.rain.usercenter.annotation.VerifyParameters;
import com.crazy.rain.usercenter.common.BaseResponse;
import com.crazy.rain.usercenter.common.ErrorCode;
import com.crazy.rain.usercenter.common.ResultUtil;
import com.crazy.rain.usercenter.exception.BasisException;
import com.crazy.rain.usercenter.model.domain.User;
import com.crazy.rain.usercenter.model.dto.QueryUserByTag;
import com.crazy.rain.usercenter.model.request.*;
import com.crazy.rain.usercenter.model.vo.PageVo;
import com.crazy.rain.usercenter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.crazy.rain.usercenter.constant.UserConstant.REQUEST_PARAMETER_IS_EMPTY;
import static com.crazy.rain.usercenter.constant.UserConstant.USER_DOES_NOT_HAVE_PERMISSION;

/**
 * @ClassName: UserController
 * @Description: 用户接口
 * @author: CrazyRain
 * @date: 2024/3/23 20:07
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
@Tag(name = "用户接口")
public class UserController {


    private final UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest 用户注册参数
     * @return 用户id或-1
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public BaseResponse<Long> userRegistration(@RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("用户注册请求:{}", userRegisterRequest);
        if (StringUtils.isAnyBlank(userRegisterRequest.getUserPassword(),
                userRegisterRequest.getUserAccount(),
                userRegisterRequest.getCheckPassword(),
                userRegisterRequest.getPlanetCode()
        )) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, REQUEST_PARAMETER_IS_EMPTY);
        }
        return ResultUtil.success(userService.registrationVerification(userRegisterRequest.getUserAccount(),
                userRegisterRequest.getUserPassword(),
                userRegisterRequest.getCheckPassword(),
                userRegisterRequest.getPlanetCode()));
    }

    /**
     * 用户登录接口
     *
     * @param userLoginRequest 用户账号密码
     * @param request          请求对象
     * @return 用户信息
     */
    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        log.info("用户登录:{}", userLoginRequest);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, REQUEST_PARAMETER_IS_EMPTY);
        }
        return ResultUtil.success(userService.userLogin(userAccount, userPassword, request));
    }

    /**
     * 查询用户信息
     *
     * @param request 当前请求
     * @return 用户信息列表
     */
    @PostMapping("/searchUser")
    public BaseResponse<List<User>> searchUser(@RequestBody ConditionalQueryUser conditionalQueryUser, HttpServletRequest request) {

        if (!userService.authentication(request)) {
            throw new BasisException(ErrorCode.NO_PERMISSION, USER_DOES_NOT_HAVE_PERMISSION);
        }

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(StringUtils.isNoneBlank(conditionalQueryUser.getUsername()), User::getUsername, conditionalQueryUser.getUserStatus())
                .like(StringUtils.isNotEmpty(conditionalQueryUser.getUserAccount()), User::getUserAccount, conditionalQueryUser.getUserAccount())
                .eq(conditionalQueryUser.getId() != null, User::getId, conditionalQueryUser.getId())
                .eq(conditionalQueryUser.getGender() != null, User::getGender, conditionalQueryUser.getGender())
                .like(StringUtils.isNotEmpty(conditionalQueryUser.getEmail()), User::getEmail, conditionalQueryUser.getEmail())
                .like(StringUtils.isNotEmpty(conditionalQueryUser.getPhone()), User::getPhone, conditionalQueryUser.getPhone())
                .eq(conditionalQueryUser.getUserStatus() != null, User::getUserStatus, conditionalQueryUser.getUserStatus())
                .eq(conditionalQueryUser.getUserRole() != null, User::getUserRole, conditionalQueryUser.getUserRole())
                .eq(conditionalQueryUser.getCreateTime() != null, User::getCreateTime, conditionalQueryUser.getCreateTime());
        IPage<User> page = new Page<>(conditionalQueryUser.getCurrent(), conditionalQueryUser.getPageSize());
        userService.page(page, userLambdaQueryWrapper);
        log.info("分页查询用户信息；{}", page.getRecords());
        return ResultUtil.success(page.getRecords());
    }

    /**
     * 根据id删除用户信息
     *
     * @param id      用户id
     * @param request 当前请求
     * @return 是否成功
     */
    @DeleteMapping("/deleteById")
    public BaseResponse<Boolean> deleteById(Integer id, HttpServletRequest request) {
        if (!userService.authentication(request)) {
            throw new BasisException(ErrorCode.NO_PERMISSION, "该用户无权限");
        }
        if (id <= 0) {
            throw new BasisException(ErrorCode.REQUEST_PARAMETERS_ERROR, "请求参数小于0");
        }
        return ResultUtil.success(userService.removeById(id));
    }


    /**
     * 查询当前用户信息
     *
     * @param request 当前请求域
     * @return 用户信息
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User attribute = userService.getLoginUser(request);
        if (attribute == null) {
            throw new BasisException(ErrorCode.NOT_LOGGED_IN, "未登录");
        }
        User user = userService.getById(attribute.getId());
        return ResultUtil.success(userService.dataDesensitization(user));
    }


    /**
     * 退出登录
     *
     * @param session 当前会话
     * @return 1 成功登录
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpSession session) {
        return ResultUtil.success(userService.logout(session));
    }


    /**
     * 设置用户基础信息
     *
     * @param userInformationRequest 用户基础信息
     * @return 是否成功更新
     */
    @PostMapping("/setUserBasicInformation")
    public BaseResponse<Void> setUserBasicInformation(@RequestBody BasicUserInformationRequest userInformationRequest) {
        log.info("设置用户基础信息:{}", userInformationRequest);
        if (StringUtils.isAnyBlank(userInformationRequest.getEmail(), userInformationRequest.getUsername(), userInformationRequest.getPhone(),
                userInformationRequest.getAvatarUrl())) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, "email、phone、username其中一项为空");
        }
        if (userInformationRequest.getId() == null || userInformationRequest.getGender() == null) {
            throw new BasisException(ErrorCode.REQUEST_PARAMETERS_ERROR, "性别或id为空");
        }
        if (userService.setUserBasicInformation(userInformationRequest)) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, "设置用户信息失败");
    }

    /**
     * 头像上传
     *
     * @param file 头像
     * @return 访问路径
     */
    @PostMapping("/pictureUpload")
    public BaseResponse<String> pictureUpload(MultipartFile file) {
        log.info("头像上传");
        if (file == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, "文件为空");
        }
        return ResultUtil.success(userService.pictureUpload(file));
    }

    /**
     * 修改该用户信息
     *
     * @return 1 成功
     */
    @PostMapping("/update")
    @VerifyParameters
    public BaseResponse<Void> updateUser(@RequestBody User user, HttpServletRequest request) {
        if (!userService.authentication(request) && !userService.getLoginUser(request).getId().equals(user.getId())) {
            return ResultUtil.error(ErrorCode.NO_PERMISSION, "该用户无权限");
        }
        log.info("修改用户信息:{}", user);
        boolean flag = userService.updateById(user);
        if (!flag) {
            return ResultUtil.error(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, "修改用户信息失败");
        }
        return ResultUtil.success();
    }

    /**
     * 创建信息用户
     *
     * @param user    用户信息
     * @param request 当前请求
     */
    @PostMapping("/insertUser")
    public BaseResponse<Void> insertUser(@RequestBody User user, HttpServletRequest request) {
        if (!userService.authentication(request)) {
            return ResultUtil.error(ErrorCode.NO_PERMISSION, "当前用户无权限创建用户");
        }
        if (StringUtils.isAnyBlank(user.getUserAccount(), user.getUserPassword(), user.getPlanetCode())) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, REQUEST_PARAMETER_IS_EMPTY);
        }
        if (!userService.insertUser(user)) {
            ResultUtil.error(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, "添加用户失败");
        }
        return ResultUtil.success();
    }

    /**
     * 根据标签搜索用户信息
     *
     * @param queryUserByTag 标签
     * @return 用户信息
     */
    @Operation(summary = "根据标签搜索用户信息")
    @GetMapping("/search/tagList")
    public BaseResponse<PageVo<List<User>>> queryUsersBasedOnTags(HttpServletRequest request, QueryUserByTag queryUserByTag) {
        log.info("根据标签搜索用户：{}", queryUserByTag);
        if (CollectionUtils.isEmpty(queryUserByTag.getTags())) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, REQUEST_PARAMETER_IS_EMPTY);
        }
        return ResultUtil.success(userService.queryUsersBasedOnTags(userService.getLoginUser(request), queryUserByTag));
    }

    @GetMapping("/userRecommendations")
    @Operation(summary = "用户匹配")
    public BaseResponse<PageVo<List<User>>> userRecommendations(PageRequest pageRequest, HttpServletRequest request) {
        return ResultUtil.success(userService.userRecommendations(pageRequest, request));
    }

    @GetMapping("/sendVerificationCode")
    @Operation(summary = "获取验证码")
    public BaseResponse<Integer> sendVerificationCode(String email) {
        return ResultUtil.success(userService.sendVerificationCode(email));
    }

}
