package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mmplanet.cloud.app.goods.domain.constraints.ValidationGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

/**
 * 商品属性表
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/17 12:55 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_attribute")
public class AttributeEntity implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    @NotBlank(groups = {ValidationGroup.MODIFY.class, ValidationGroup.DEL.class})
    private String id;

    /**
     * 属性名称
     */
    @TableField("name")
    @NotBlank(message = "属性名称必填",groups={ValidationGroup.INSERT.class,ValidationGroup.MODIFY.class})
    private String name;

    /**
     * 排序
     */
    @TableField("sort")
    @NotNull(message = "排序必填",groups={ValidationGroup.INSERT.class,ValidationGroup.MODIFY.class})
    private Integer sort;

    /**
     * 输入类型
     */
    @TableField("input_type")
    private String inputType;

    /**
     * 是否必选/填
     */
    @TableField("needed")
    private Integer needed;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 备选值
     */
    @TableField("select_value")
    @NotBlank(message = "属性值",groups={ValidationGroup.INSERT.class,ValidationGroup.MODIFY.class})
    private String selectValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    @NotBlank(groups = {ValidationGroup.INSERT.class})
    private String createUser;

    /**
     * 最近修改时间
     */
    private Date lastUpdateTime;

    /**
     * 修改人
     */
    @TableField("update_user")
    @NotBlank(groups = {ValidationGroup.MODIFY.class, ValidationGroup.DEL.class})
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
