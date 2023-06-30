package com.seeleaf.guitarbackend.constant;

/**
 * 用户常量
 *
 * @author seeleaf
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 盐值 混淆密码
     */
    String SALT = "seeleaf";


    //  ------- 权限 --------

    /**
     * 超级管理员
     */
    String SUPER_ADMIN_ROLE = "superAdmin";
    /**
     * 管理员
     */
    String ADMIN_ROLE = "admin";

}
