package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateEntity;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateRuleEntity;

import java.util.List;

/**
 * <p>
 * 运费模板 服务类
 * </p>
 *
 * @author niujiao
 * @since 2022-06-08
 */
public interface FreightTemplateService extends IService<FreightTemplateEntity> {


    boolean save(FreightTemplateEntity entity, List<FreightTemplateRuleEntity> rules);

    boolean modify(FreightTemplateEntity entity, List<FreightTemplateRuleEntity> rules);
}
