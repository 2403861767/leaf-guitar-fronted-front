package com.seeleaf.guitarbackend.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName music
 */
@TableName(value ="music")
@Data
public class Music implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}