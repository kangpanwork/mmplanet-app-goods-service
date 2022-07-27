package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 运费模板明细规格
 * </p>
 *
 * @author niujiao
 * @since 2022-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_freight_template_rule")
public class FreightTemplateRuleEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 计费方式：按件、重量和体积
     */
    private String chargeWay;

    /**
     * 首费
     */
    private BigDecimal firstCharge;

    /**
     * 首费范围
     */
    private BigDecimal firstChargeRange;

    /**
     * 续费条件
     */
    private BigDecimal renewChargeStep;
    /**
     * 续费
     */
    private BigDecimal renewCharge;

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
