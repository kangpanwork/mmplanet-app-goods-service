package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.dto.FreightTemplateDto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateRuleDto;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateEntity;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateRuleEntity;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateListVo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateRuleVo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 14:52 <br>
 * @Author: niujiao
 */
@Mapper(componentModel = "spring")
public interface FreightTemplateTransfer {
    FreightTemplateEntity convertFreightTemplate(FreightTemplateDto dto);

    FreightTemplateRuleEntity convertFreightTemplateRule(FreightTemplateRuleDto dto);

    List<FreightTemplateListVo> assembleFreightTemplateListVo(List<FreightTemplateEntity> records);

    FreightTemplateVo assembleFreightTemplateVo(FreightTemplateEntity freightTemplateEntity);

    List<FreightTemplateRuleVo> assembleFreightTemplateVo(List<FreightTemplateRuleEntity> ruleEntities);
}
