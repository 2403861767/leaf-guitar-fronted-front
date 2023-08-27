package com.seeleaf.guitarbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seeleaf.guitarbackend.domain.entity.Music;
import com.seeleaf.guitarbackend.service.MusicService;
import com.seeleaf.guitarbackend.mapper.MusicMapper;
import org.springframework.stereotype.Service;

/**
* @author 24038
* @description 针对表【music(用户)】的数据库操作Service实现
* @createDate 2023-08-27 13:51:47
*/
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music>
    implements MusicService{

}




