package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.service.CategoryAttributeService;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryAttributeEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.CategoryAttributeMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;


@Service
public class CategoryAttributeServiceImpl extends ServiceImpl<CategoryAttributeMapper, CategoryAttributeEntity> implements CategoryAttributeService {

    @Override
    public Boolean removeById(Integer id, String opUserId) {
        UpdateWrapper<CategoryAttributeEntity> updateWrapper =
                new UpdateWrapper<CategoryAttributeEntity>()
                        .set("update_user", id)
                        .set("update_time", new Date())
                        .set("deleted", TRUE.getValue())
                        .eq("id", id);
        return update(updateWrapper);
    }
}



