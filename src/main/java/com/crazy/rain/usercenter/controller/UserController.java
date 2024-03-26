package com.crazy.rain.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.crazy.rain.usercenter.common.BaseResponse;
import com.crazy.rain.usercenter.common.ErrorCode;
import com.crazy.rain.usercenter.common.ResultUtil;
import com.crazy.rain.usercenter.constant.UserConstant;
import com.crazy.rain.usercenter.exception.BasisException;
import com.crazy.rain.usercenter.model.domain.User;
import com.crazy.rain.usercenter.model.request.UserLoginRequest;
import com.crazy.rain.usercenter.model.request.UserRegisterRequest;
import com.crazy.rain.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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
@CrossOrigin(originPatterns = "*")
public class UserController {

    private final UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest 用户注册参数
     * @return 用户id或-1
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegistration(@RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("用户注册请求:{}", userRegisterRequest);
        if (StringUtils.isAnyBlank(userRegisterRequest.getUserPassword(),
                userRegisterRequest.getUserAccount(),
                userRegisterRequest.getCheckPassword(),
                userRegisterRequest.getPlanetCode()
        )) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, "请求参数为空");
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
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST, "请求参数为空");
        }
        return ResultUtil.success(userService.userLogin(userAccount, userPassword, request));
    }

    /**
     * 查询用户信息
     *
     * @param username 用户名
     * @param request  当前请求
     * @return 用户信息列表
     */
    @GetMapping("/searchUser")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        if (!authentication(request)) {
            throw new BasisException(ErrorCode.NO_PERMISSION, "该用户无权限");
        }

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(StringUtils.isNoneBlank(username), User::getUsername, username);
        return ResultUtil.success(userService.list(userLambdaQueryWrapper).stream().map(userService::dataDesensitization).collect(Collectors.toList()));
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
        if (!authentication(request)) {
            throw new BasisException(ErrorCode.NO_PERMISSION, "该用户无权限");
        }
        if (id <= 0) {
            throw new BasisException(ErrorCode.REQUEST_PARAMETERS_ERROR, "请求参数小于0");
        }
        return ResultUtil.success(userService.removeById(id));
    }

    private boolean authentication(HttpServletRequest request) {
        User attribute = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return attribute != null && attribute.getUserRole() != UserConstant.DEFAULT_USER;
    }

    /**
     * 查询当前用户信息
     *
     * @param session 当前会话域
     * @return 用户信息
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpSession session) {
        User attribute = (User) session.getAttribute(UserConstant.USER_LOGIN_STATE);
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
}
