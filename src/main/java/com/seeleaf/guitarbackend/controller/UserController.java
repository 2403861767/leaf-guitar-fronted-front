package com.seeleaf.guitarbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seeleaf.guitarbackend.common.*;
import com.seeleaf.guitarbackend.domain.entity.User;
import com.seeleaf.guitarbackend.domain.request.user.LoginUserRequest;
import com.seeleaf.guitarbackend.domain.request.user.RegisterUserRequest;
import com.seeleaf.guitarbackend.domain.request.user.UpdateUserRequest;
import com.seeleaf.guitarbackend.domain.vo.UserVo;
import com.seeleaf.guitarbackend.exception.BusinessException;
import com.seeleaf.guitarbackend.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.seeleaf.guitarbackend.common.CommonFunctions.validateParamIsNull;
import static com.seeleaf.guitarbackend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/test")
//    public BaseResponse test() {
//        return ResultUtils.success(null);
//    }

    // 增删改查
    @PostMapping("/add")
    @ApiOperation("增加")
    public BaseResponse<Long> add(@RequestBody User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        long userId = userService.addUser(user);
        return ResultUtils.success(userId);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public BaseResponse<Boolean> delete(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        long id = deleteRequest.getId();
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        boolean isSuccess = userService.removeById(id);
        return ResultUtils.success(isSuccess);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public BaseResponse<Boolean> update(@RequestBody UpdateUserRequest updateRequest) {
        validateParamIsNull(updateRequest);
        Long id = updateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        // TODO 校验是账号是否已经存在
        User updateUser = BeanUtil.copyProperties(updateRequest, User.class);
        boolean isSuccess = userService.updateById(updateUser);
        return ResultUtils.success(isSuccess);
    }

    @GetMapping("/list")
    @ApiOperation("查询全部")
    public BaseResponse<List<UserVo>> list() {
        List<User> list = userService.list();
        List<UserVo> userVos = BeanUtil.copyToList(list, UserVo.class);
        return ResultUtils.success(userVos);
    }

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public BaseResponse<PageVo> page(PageRequest pageRequest) {
        validateParamIsNull(pageRequest);
        int pageSize = pageRequest.getPageSize();
        int pageNum = pageRequest.getPageNum();
        if (pageSize <= 0 || pageNum <=0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        // TODO 用searchText进行条件查询
        // TODO 如果current * pageSize > total 还是会查数据库
        Page<User> userPage = new Page<User>(pageNum,pageSize);
        Page<User> record = userService.page(userPage);
        List<User> list = record.getRecords();
        List<UserVo> userVos = BeanUtil.copyToList(list, UserVo.class);
        PageVo pageVo = new PageVo();
        pageVo.setData(userVos);
        pageVo.setTotal(record.getTotal());
        pageVo.setPageSize(record.getSize());
        pageVo.setCurrent(record.getCurrent());
        return ResultUtils.success(pageVo);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("查询单个")
    public BaseResponse<UserVo> getById(@PathVariable Long id){
        if (id == null || id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        User user = userService.getById(id);
        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);
        return ResultUtils.success(userVo);

    }


    // 登录和注册
    @PostMapping("/login")
    @ApiOperation("登录")
    public BaseResponse<Long> login(@RequestBody LoginUserRequest loginUserRequest, HttpServletRequest request){
        validateParamIsNull(loginUserRequest);
        Long id = userService.login(loginUserRequest,request);
        return ResultUtils.success(id);

    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public BaseResponse<Long> register(@RequestBody RegisterUserRequest registerUserRequest, HttpServletRequest request){
        validateParamIsNull(registerUserRequest);
        Long id =  userService.register(registerUserRequest, request);
        return ResultUtils.success(id);
    }

    @PostMapping("/logout")
    @ApiOperation("登出")
    public BaseResponse logout(HttpServletRequest request){
        validateParamIsNull(request);
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success();
    }
}
