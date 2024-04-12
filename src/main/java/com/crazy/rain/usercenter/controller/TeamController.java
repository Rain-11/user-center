package com.crazy.rain.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.crazy.rain.usercenter.annotation.VerifyParameters;
import com.crazy.rain.usercenter.common.BaseResponse;
import com.crazy.rain.usercenter.common.ErrorCode;
import com.crazy.rain.usercenter.common.ResultUtil;
import com.crazy.rain.usercenter.exception.BasisException;
import com.crazy.rain.usercenter.model.domain.Team;
import com.crazy.rain.usercenter.model.domain.TeamUser;
import com.crazy.rain.usercenter.model.domain.User;
import com.crazy.rain.usercenter.model.dto.QueryTeamDto;
import com.crazy.rain.usercenter.model.dto.TeamDto;
import com.crazy.rain.usercenter.model.request.JoinTeam;
import com.crazy.rain.usercenter.model.request.PageRequest;
import com.crazy.rain.usercenter.model.vo.PageVo;
import com.crazy.rain.usercenter.model.vo.TeamVo;
import com.crazy.rain.usercenter.service.TeamService;
import com.crazy.rain.usercenter.service.TeamUserService;
import com.crazy.rain.usercenter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.crazy.rain.usercenter.constant.TeamConstant.DELETE_TEAM_ERROR;
import static com.crazy.rain.usercenter.constant.TeamConstant.UPDATE_TEAM_ERROR;

/**
 * @ClassName: TeamController
 * @Description: 队伍接口
 * @author: CrazyRain
 * @date: 2024/4/5 13:39
 */
@RestController
@Slf4j
@RequestMapping("/team")
@AllArgsConstructor
@Tag(name = "队伍接口")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamUserService teamUserService;

    @PostMapping("/create")
    @Operation(summary = "创建队伍")
    public BaseResponse<Long> createTeam(@RequestBody TeamDto createTeamDto, HttpServletRequest request) {
        if (createTeamDto == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BasisException(ErrorCode.NOT_LOGGED_IN);
        }
        return ResultUtil.success(teamService.createTeam(createTeamDto, loginUser));
    }

    @PutMapping("/update")
    @Operation(summary = "修改队伍信息")
    @VerifyParameters
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamDto createTeamDto, HttpServletRequest request) {
        if (createTeamDto == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        Team team = teamService.teamConverter(createTeamDto);
        log.info("修改队伍基本信息:{}", createTeamDto);
        boolean save = teamService.updateTeam(team, userService.getLoginUser(request));
        if (!save) {
            throw new BasisException(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, UPDATE_TEAM_ERROR);
        }
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除队伍信息")
    public BaseResponse<Boolean> deleteTeam(Long id) {
        if (id == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        boolean save = teamService.removeById(id);
        if (!save) {
            throw new BasisException(ErrorCode.INTERNAL_SYSTEM_EXCEPTION, DELETE_TEAM_ERROR);
        }
        return ResultUtil.success(true);
    }

    @PostMapping("/query")
    @Operation(summary = "查询队伍信息")
    public BaseResponse<PageVo<List<TeamVo>>> query(@RequestBody QueryTeamDto queryTeamDto, HttpServletRequest request) {
        if (queryTeamDto == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }

        return ResultUtil.success(teamService.queryList(queryTeamDto
                , userService.authentication(request)
                , userService.getLoginUser(request)));
    }


    @PostMapping("/joinTeam")
    @Operation(summary = "加入队伍")
    public BaseResponse<Boolean> joinTeam(@RequestBody JoinTeam joinTeam, HttpServletRequest request) {
        if (joinTeam == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        return ResultUtil.success(teamService.joinTeam(joinTeam, userService.getLoginUser(request)));
    }

    @PostMapping("/exitTeam")
    @Operation(summary = "退出队伍")
    public BaseResponse<Void> exitTeam(Long teamId, HttpServletRequest request) {
        if (teamId == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        teamService.exitTeam(teamId, userService.getLoginUser(request));
        return ResultUtil.success();
    }

    @PostMapping("/dissolveTeam")
    @Operation(summary = "解散队伍")
    public BaseResponse<Void> dissolveTeam(Long teamId, HttpServletRequest request) {
        if (teamId == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        teamService.dissolveTeam(teamId, userService.getLoginUser(request));
        return ResultUtil.success();
    }

    @GetMapping("getTeamCount")
    @Operation(summary = "通过队伍id获取队伍人数")
    public BaseResponse<Long> getTeamCount(Long teamId) {
        if (teamId == null) {
            throw new BasisException(ErrorCode.PARAMETER_DOES_NOT_EXIST);
        }
        LambdaQueryWrapper<TeamUser> teamLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teamLambdaQueryWrapper.eq(TeamUser::getTeamId, teamId);
        return ResultUtil.success(teamUserService.count(teamLambdaQueryWrapper));
    }

    @GetMapping("/obtainingJoinedTeams")
    @Operation(summary = "获取登录用户已加入的队伍")
    public BaseResponse<List<TeamVo>> obtainingJoinedTeams(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtil.success(teamService.obtainingJoinedTeams(loginUser));
    }

    @GetMapping("/obtainCreatedTeam")
    @Operation(summary = "获取登录用户已创建队伍")
    public BaseResponse<List<TeamVo>> obtainCreatedTeam(PageRequest pageRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtil.success(teamService.obtainCreatedTeam(loginUser));
    }

}
