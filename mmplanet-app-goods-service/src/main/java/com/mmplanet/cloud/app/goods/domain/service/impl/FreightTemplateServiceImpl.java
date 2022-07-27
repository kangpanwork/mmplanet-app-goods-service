package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.common.util.UUIDHelper;
import com.mmplanet.cloud.app.goods.domain.service.FreightTemplateService;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateEntity;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateRuleEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.FreightTemplateMapper;
import com.mmplanet.cloud.app.goods.infra.mapper.FreightTemplateRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 运费模板 服务实现类
 * </p>
 *
 * @author niujiao
 * @since 2022-06-08
 */
@Service
public class FreightTemplateServiceImpl extends ServiceImpl<FreightTemplateMapper, FreightTemplateEntity> implements FreightTemplateService {

    @Autowired
    private FreightTemplateMapper freightTemplateMapper;

    @Autowired
    private FreightTemplateRuleMapper freightTemplateRuleMapper;

    @Override
    public boolean save(FreightTemplateEntity entity, List<FreightTemplateRuleEntity> rules) {
        entity.setCreateTime(new Date());
        entity.setId(UUIDHelper.getUUID());
        rules.forEach(e -> {
            e.setTemplateId(entity.getId());
            e.setCreateTime(entity.getCreateTime());
        });
        freightTemplateMapper.insert(entity);

        rules.forEach(e -> {
            freightTemplateRuleMapper.insert(e);
        });
        return false;
    }

    @Override
    public boolean modify(FreightTemplateEntity entity, List<FreightTemplateRuleEntity> rules) {
        entity.setUpdateTime(new Date());
        rules.forEach(e -> {
            e.setTemplateId(entity.getId());
            e.setUpdateTime(entity.getUpdateTime());
        });
        freightTemplateMapper.updateById(entity);

        rules.forEach(e -> {
            String ruleId = e.getId();
            if(StringUtils.isEmpty(ruleId)){
                freightTemplateRuleMapper.insert(e);
            }else{
                freightTemplateRuleMapper.updateById(e);
            }
        });

        return true;
    }
}
