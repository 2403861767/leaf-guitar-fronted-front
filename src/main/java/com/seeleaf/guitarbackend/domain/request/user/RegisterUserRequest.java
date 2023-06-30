package com.seeleaf.guitarbackend.domain.request.user;

import lombok.Data;

import java.io.Serializable;


/**
 * user update请求
 * @author seeleaf
 */
@Data
public class RegisterUserRequest implements Serializable {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 校验码
     */
    private String checkPassword;

    private static final long serialVersionUID = 1L;
}