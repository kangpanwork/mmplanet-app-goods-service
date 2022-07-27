package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryEntity;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.apache.commons.lang3.StringUtils;
import com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum;

import java.util.List;

@Mapper(componentModel = "spring",imports = {StringUtils.class,TureOrFalseEnum.class})
public interface CategoryTransfer {
    CategoryVo po2vo(CategoryEntity po);

    List<CategoryVo> listPo2listVo(List<CategoryEntity> pos);

    List<CategoryTreeVo> assembleCategoryTreeVo(List<CategoryEntity> entities);

    @Mapping(target = "hasChildren", expression = "java(TureOrFalseEnum.FALSE.getValue())")
    @Mapping(target = "pid", expression = "java(StringUtils.isEmpty(dto.getPid())? \"0\" : dto.getPid())")
    CategoryEntity convertToEntity(CategoryDto dto);

    @Mapping(target = "hasChildren", expression = "java(TureOrFalseEnum.FALSE.getValue())")
    @Mapping(target = "pid", expression = "java(StringUtils.isEmpty(dto.getPid())? \"0\" : dto.getPid())")
    CategoryEntity toModel(CategoryDto dto);
}
