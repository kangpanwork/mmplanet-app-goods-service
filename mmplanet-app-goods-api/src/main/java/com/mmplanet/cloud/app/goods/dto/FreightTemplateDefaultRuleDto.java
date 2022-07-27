package com.mmplanet.cloud.app.goods.dto;

import com.mmplanet.cloud.app.goods.enums.FreightChargeWayEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class FreightTemplateDefaultRuleDto implements Serializable {


    /**
     * 计费方式：按件、重量和体积
     * @see FreightChargeWayEnum
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
}
