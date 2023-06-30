package com.seeleaf.guitarbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seeleaf.guitarbackend.common.ErrorCode;
import com.seeleaf.guitarbackend.domain.entity.User;
import com.seeleaf.guitarbackend.domain.request.user.LoginUserRequest;
import com.seeleaf.guitarbackend.domain.request.user.RegisterUserRequest;
import com.seeleaf.guitarbackend.exception.BusinessException;
import com.seeleaf.guitarbackend.service.UserService;
import com.seeleaf.guitarbackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.seeleaf.guitarbackend.constant.UserConstant.SALT;
import static com.seeleaf.guitarbackend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 24038
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-06-28 14:41:17
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 添加用户
     * @param user 用户
     * @return id
     */
    @Override
    public long addUser(User user) {
        String userAccount = user.getUserAccount();
        String userPassword = user.getUserPassword();
        // 基本的校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或者密码为空!");
        }
        if (userAccount.length() < 4 || userAccount.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不符合要求!");
        }
        if (userPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不符合要求!");
        }
        // 校验账号是否重复
        User getUser = this.getOne(new QueryWrapper<User>().eq("userAccount", userAccount));
        if (getUser != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该账号已存在");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码包含特殊字符!");
        }
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        user.setUserPassword(encryptPassword);
        boolean isSuccess = this.save(user);
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未知错误");
        }
        return user.getId();
    }

    /**
     * 登录
     * @param loginUserRequest 登录参数
     * @param request 请求
     * @return id
     */
    @Override
    public Long login(LoginUserRequest loginUserRequest, HttpServletRequest request) {
        String verifyCode = loginUserRequest.getVerifyCode();
        String userAccount = loginUserRequest.getUserAccount();
        String userPassword = loginUserRequest.getUserPassword();
        if (StringUtils.isAnyBlank(verifyCode,userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或者密码为空!!");
        }
        // 再校验一下账号和密码的长度
        if (userAccount.length() < 4 || userAccount.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不符合要求!");
        }
        if (userPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不符合要求!");
        }
        // TODO 验证码稍后处理

        // 校验 账号和密码是否匹配
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 去数据库匹配加密后的密码
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,userAccount)
                .eq(User::getUserPassword,encryptPassword);
        User user = this.getOne(queryWrapper);
        if (user == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "密码或者用户名错误!");
        }
        // 记住用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user.getId();
    }

    @Override
    public Long register(RegisterUserRequest registerUserRequest, HttpServletRequest request) {
        String checkPassword = registerUserRequest.getCheckPassword();
        String userPassword = registerUserRequest.getUserPassword();
        String userAccount = registerUserRequest.getUserAccount();
        if (StringUtils.isAnyBlank(checkPassword,userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或者密码为空!!");
        }
        // 再校验一下账号和密码的长度
        if (userAccount.length() < 4 || userAccount.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不符合要求!");
        }
        if (userPassword.length() < 6 || checkPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码或者校验密码不符合要求!");
        }
        if (!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致!");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号有非法字符!");
        }
        synchronized (userAccount.intern()){
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                log.info("账号重复");
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                log.info("发生未知错误");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }


}




