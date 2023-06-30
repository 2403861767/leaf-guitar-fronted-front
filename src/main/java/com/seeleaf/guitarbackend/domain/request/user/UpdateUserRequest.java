package com.seeleaf.guitarbackend.domain.request.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * user update请求
 * @author seeleaf
 */
@Data
public class UpdateUserRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private String gender;


    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户状态 0表示正常,1表示会员，2表示封号
     */
    private Integer userStatus;

    /**
     * 用户角色：user/genealogist/sharer/admin/superAdmin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}