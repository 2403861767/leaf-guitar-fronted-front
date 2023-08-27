package com.seeleaf.guitarbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seeleaf.guitarbackend.common.*;
import com.seeleaf.guitarbackend.domain.entity.Music;
import com.seeleaf.guitarbackend.domain.entity.User;
import com.seeleaf.guitarbackend.domain.request.music.UpdateMusicRequest;
import com.seeleaf.guitarbackend.domain.request.user.LoginUserRequest;
import com.seeleaf.guitarbackend.domain.request.user.RegisterUserRequest;
import com.seeleaf.guitarbackend.domain.request.user.UpdateUserRequest;
import com.seeleaf.guitarbackend.domain.vo.UserVo;
import com.seeleaf.guitarbackend.exception.BusinessException;
import com.seeleaf.guitarbackend.service.MusicService;
import com.seeleaf.guitarbackend.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.seeleaf.guitarbackend.common.CommonFunctions.validateParamIsNull;
import static com.seeleaf.guitarbackend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private UserService userService;

    @Autowired
    private MusicService musicService;

    // 曲谱推荐接口(按照周人气推荐12个)
    @ApiOperation("曲谱推荐")
    @PostMapping("/recommend")
    public BaseResponse<List<Music>> recommend(){
        // TODO 曲谱推荐具体实现
        Page<Music> musicPage = new Page<Music>(1,12);
        Page<Music> record = musicService.page(musicPage);
        List<Music> list = record.getRecords();
        return ResultUtils.success(list);
    }


    // 增删改查
    @PostMapping("/add")
    @ApiOperation("增加")
    public BaseResponse<Long> add(@RequestBody Music music) {
        if (music == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        boolean flag = musicService.save(music);
        if (!flag){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "增加失败!");
        }
        return ResultUtils.success(music.getId());
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
        boolean isSuccess = musicService.removeById(id);
        return ResultUtils.success(isSuccess);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public BaseResponse<Boolean> update(@RequestBody UpdateMusicRequest updateRequest) {
        validateParamIsNull(updateRequest);
        Long id = updateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        // TODO 校验是账号是否已经存在
        Music updateMusic = BeanUtil.copyProperties(updateRequest, Music.class);
        boolean isSuccess = musicService.updateById(updateMusic);
        return ResultUtils.success(isSuccess);
    }

    @GetMapping("/list")
    @ApiOperation("查询全部")
    public BaseResponse<List<Music>> list() {
        List<Music> list = musicService.list();
//        List<UserVo> userVos = BeanUtil.copyToList(list, UserVo.class);
        return ResultUtils.success(list);
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
        Page<Music> musicPage = new Page<Music>(pageNum,pageSize);
        Page<Music> record = musicService.page(musicPage);
        List<Music> list = record.getRecords();
        PageVo pageVo = new PageVo();
        pageVo.setData(list);
        pageVo.setTotal(record.getTotal());
        pageVo.setPageSize(record.getSize());
        pageVo.setCurrent(record.getCurrent());
        return ResultUtils.success(pageVo);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("查询单个")
    public BaseResponse<Music> getById(@PathVariable Long id){
        if (id == null || id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
        Music music = musicService.getById(id);
        return ResultUtils.success(music);

    }


}
