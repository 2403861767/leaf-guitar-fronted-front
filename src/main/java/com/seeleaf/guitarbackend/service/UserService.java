package com.seeleaf.guitarbackend.service;

import com.seeleaf.guitarbackend.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seeleaf.guitarbackend.domain.request.user.LoginUserRequest;
import com.seeleaf.guitarbackend.domain.request.user.RegisterUserRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author 24038
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-06-28 14:41:17
*/
public interface UserService extends IService<User> {


    long addUser(User user);

    Long login(LoginUserRequest loginUserRequest, HttpServletRequest request);

    Long register(RegisterUserRequest registerUserRequest, HttpServletRequest request);
}
