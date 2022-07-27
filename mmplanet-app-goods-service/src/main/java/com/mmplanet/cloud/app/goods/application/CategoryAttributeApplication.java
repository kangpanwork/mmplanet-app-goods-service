package com.mmplanet.cloud.app.goods.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmplanet.cloud.app.goods.domain.service.CategoryAttributeService;
import com.mmplanet.cloud.app.goods.dto.AssociateAttrDto;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryAttributeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/20 16:22 <br>
 * @Author: niujiao
 */
@Component
public class CategoryAttributeApplication {

    @Autowired
    private CategoryAttributeService categoryAttributeService;

    @Transactional(rollbackFor = Exception.class)
    public Boolean save(AssociateAttrDto dto) {
        dto.getAttributeIds().forEach(e -> {
            try {
                CategoryAttributeEntity entity = new CategoryAttributeEntity();
                entity.setCategoryId(dto.getCategoryId());
                entity.setAttributeId(e);
                entity.setCreateTime(new Date());
                entity.setCreateUser(dto.getOpUserId());
                categoryAttributeService.save(entity);
            } catch (DuplicateKeyException ignore) {

            }
        });
        return true;
    }

    private void removeExistedData(AssociateAttrDto dto) {

        LambdaQueryWrapper<CategoryAttributeEntity> queryCondition = Wrappers.<CategoryAttributeEntity>lambdaQuery()
                .eq(CategoryAttributeEntity::getAttributeId, dto.getCategoryId());

        List<CategoryAttributeEntity> list = categoryAttributeService.list(queryCondition);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(e -> {
                categoryAttributeService.removeById(e.getId(),dto.getOpUserId());
            });
        }
    }

}
