package com.mmplanet.cloud.app.goods.vo;

import com.mmplanet.cloud.app.goods.dto.FreightTemplateDefaultRuleDto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateRuleDto;
import com.mmplanet.cloud.app.goods.enums.FreightTemplateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/25 10:15 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FreightTemplateVo implements Serializable {

    /**
     * 模板ID
     */
    private String id;
    /**
     * 模板名字
     */
    private String name;

    /**
     * 模板类型
     */
    private String type;

    private List<FreightTemplateRuleVo> rules;

    private FreightTemplateDefaultRuleVo defaultRule;
}
