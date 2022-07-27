package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 13:55 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FreightTemplateRuleVo implements Serializable {

    /**
     * 模板规则ID
     */
    private String id;

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
}
