package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmplanet.cloud.app.goods.domain.service.AttributeService;
import com.mmplanet.cloud.app.goods.infra.entity.AttributeEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.AttributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;


@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeMapper attributeMapper;

    @Override
    public Boolean removeById(AttributeEntity entity) {
        entity.setLastUpdateTime(new Date());
        UpdateWrapper<AttributeEntity> updateCondition =
                new UpdateWrapper<AttributeEntity>()
                        .set("deleted",TRUE.getValue())
                        .eq("id", entity.getId());
        return attributeMapper.update(entity, updateCondition) == 1;
    }

    @Override
    public boolean modify(AttributeEntity entity) {
        entity.setLastUpdateTime(new Date());
        return attributeMapper.updateById(entity) == 1;
    }

    @Override
    public boolean insert(AttributeEntity entity) {
        entity.setCreateTime(new Date());
        return attributeMapper.insert(entity) == 1;
    }

    @Override
    public List<AttributeEntity> list(LambdaQueryWrapper<AttributeEntity> attrQuery) {
        return attributeMapper.selectList(attrQuery);
    }

    @Override
    public AttributeEntity getById(String id) {
        return attributeMapper.selectById(id);
    }

    @Override
    public IPage<AttributeEntity> page(Page<AttributeEntity> pageParam, LambdaQueryWrapper<AttributeEntity> attrQuery) {
        return attributeMapper.selectPage(pageParam, attrQuery);
    }
}



