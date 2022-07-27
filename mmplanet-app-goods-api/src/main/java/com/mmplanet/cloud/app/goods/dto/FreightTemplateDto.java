package com.mmplanet.cloud.app.goods.dto;

import com.mmplanet.cloud.app.goods.constraints.ValueOfEnum;
import com.mmplanet.cloud.app.goods.enums.FreightChargeWayEnum;
import com.mmplanet.cloud.app.goods.enums.FreightTemplateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 13:32 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FreightTemplateDto implements Serializable {

    private String id;
    /**
     * 模板名字
     */
    @NotBlank(message="模板名字必填")
    private String name;


    private String userId;
    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 模板类型
     * @see FreightTemplateTypeEnum
     */
    @ValueOfEnum(enumClass = FreightTemplateTypeEnum.class)
    @NotBlank(message="模板类型必填")
    private String type;

    /**
     * 计费方式：按件、重量和体积
     * @see FreightChargeWayEnum
     */
    @ValueOfEnum(enumClass = FreightChargeWayEnum.class)
    @NotBlank(message="计费方式必填")
    private String chargeWay;
    /**
     * 模板规则
     */
    @Valid
    private List<FreightTemplateRuleDto> rules;

    @NotNull(message="默认运费必填")
    private FreightTemplateDefaultRuleDto defaultRule;
}
