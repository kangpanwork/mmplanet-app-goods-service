package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmplanet.cloud.app.goods.domain.constraints.CheckAttributeIdExist;
import com.mmplanet.cloud.app.goods.domain.constraints.CheckAttributeNoDuplication;
import com.mmplanet.cloud.app.goods.infra.entity.AttributeEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface AttributeService {

    Boolean removeById(@Valid @NotNull @CheckAttributeIdExist AttributeEntity entity);

    boolean modify(@Valid @NotNull @CheckAttributeIdExist @CheckAttributeNoDuplication AttributeEntity entity);

    boolean insert(@Valid @NotNull @CheckAttributeNoDuplication AttributeEntity entity);

    List<AttributeEntity> list(LambdaQueryWrapper<AttributeEntity> attrQuery);

    AttributeEntity getById(String id);

    IPage<AttributeEntity> page(Page<AttributeEntity> pageParam, LambdaQueryWrapper<AttributeEntity> attrQuery);
}
