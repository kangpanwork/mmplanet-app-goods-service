package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 运费模板
 * </p>
 *
 * @author niujiao
 * @since 2022-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_freight_template")
public class FreightTemplateEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 模板名字
     */
    private String name;

    /**
     * 模板类型
     */
    private String type;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 计费类型
     */
    private String chargeType;

    /**
     * 扩展字段
     */
    private String extendField;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 创建人
     */
    private String updateUser;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;
}
