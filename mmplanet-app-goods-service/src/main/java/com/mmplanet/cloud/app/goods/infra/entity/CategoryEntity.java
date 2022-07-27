package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品目录表
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/17 12:55 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_category")
public class CategoryEntity implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    /**
     * 父主键
     */
    @TableField("pid")
    private String pid;

    /**
     * 目录名称
     */
    @TableField("name")
    private String name;

    /**
     * 目录路径
     */
    @TableField("id_path")
    private String idPath;

    /**
     * 路径名称
     */
    @TableField("name_path")
    private String namePath;

    /**
     * 是否有子目录
     */
    @TableField("has_children")
    private Integer hasChildren;

    /**
     * 分类排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 跨境税率
     */
    @TableField("cross_rate")
    private BigDecimal crossRate;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 最近更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;

    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;
}
