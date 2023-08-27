package com.seeleaf.guitarbackend.domain.request.music;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 修改谱子参数
 */
@Data
public class UpdateMusicRequest implements Serializable {
    private Long id;

    /**
     * 音乐名
     */
    private String musicName;

    /**
     * 歌手
     */
    private String singer;

    /**
     * xx 制谱
     */
    private String producer;

    /**
     * 音乐描述
     */
    private String musicDescription;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 难度
     */
    private Integer difficult;

    /**
     * 周人气
     */
    private Integer popularity;

    /**
     * 曲谱类型
     */
    private String typeTag;

    /**
     * 谱子的状态，0为待审核,1为审核通过正常
     */
    private Integer status;



    /**
     * 原版(0-100)
     */
    private Integer originalEdition;

    /**
     * 记录上传者的id
     */
    private Long createUserId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}