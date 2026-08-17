package com.lichenxi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lichenxi.springbootinit.common.BaseResponse;
import com.lichenxi.springbootinit.common.ErrorCode;
import com.lichenxi.springbootinit.common.ResultUtils;
import com.lichenxi.springbootinit.config.WxOpenConfig;
import com.lichenxi.springbootinit.exception.BusinessException;
import com.lichenxi.springbootinit.exception.ThrowUtils;
import com.lichenxi.springbootinit.model.dto.user.UserLoginRequest;
import com.lichenxi.springbootinit.model.dto.user.UserQueryRequest;
import com.lichenxi.springbootinit.model.dto.user.UserRegisterRequest;
import com.lichenxi.springbootinit.model.dto.user.UserUpdateMyRequest;
import com.lichenxi.springbootinit.model.entity.User;
import com.lichenxi.springbootinit.model.vo.LoginUserVO;
import com.lichenxi.springbootinit.model.vo.UserVO;
import com.lichenxi.springbootinit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.lichenxi.springbootinit.constant.UserConstant.ADMIN_ROLE;
import static com.lichenxi.springbootinit.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private WxOpenConfig wxOpenConfig;

    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        // 关键：先获取session对象，强制触发session创建，服务端才返回Set‑Cookie
        HttpSession session = request.getSession();

        return ResultUtils.success(loginUserVO);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 查询用户请求
     *
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<UserVO>> searchUsers(String username, HttpServletRequest request) {
        //仅管理员可查询
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("userAccount", username);
        }
        List<User> userList = userService.list(queryWrapper);
        // 实体转VO，剔除密码等敏感字段
        List<UserVO> voList = userList.stream()
                .map(user -> userService.getUserVO(user))
                .collect(Collectors.toList());
        return ResultUtils.success(voList);
    }


    /**
     * 删除用户请求
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<LoginUserVO> getCurrentUser(HttpServletRequest request) {
        LoginUserVO loginUserVO = userService.getLoginUserVO(userService.getLoginUser(request));
        return ResultUtils.success(loginUserVO);











































    }
}