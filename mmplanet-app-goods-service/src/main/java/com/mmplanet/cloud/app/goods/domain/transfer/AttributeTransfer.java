package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.dto.AttributeDto;
import com.mmplanet.cloud.app.goods.enums.EnableOrDisEnableEnum;
import com.mmplanet.cloud.app.goods.infra.entity.AttributeEntity;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryAttributeEntity;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.OpsAttributeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Arrays.class, EnableOrDisEnableEnum.class, Date.class})
public interface AttributeTransfer {


    List<AttributeVo> assembleAttributeVo(List<AttributeEntity> attributeEntities);

    @Mapping(target = "attrValues", expression = "java(Arrays.asList(attributeEntity.getSelectValue().split(\",\")))")
    @Mapping(target = "attrName", source = "name")
    AttributeVo assembleAttributeVo(AttributeEntity attributeEntity);

    @Mapping(target = "selectValue", expression = "java(String.join(\",\",dto.getSelectValues()))")
    @Mapping(target = "createTime", expression = "java(new Date())")
    @Mapping(target = "status", expression = "java(EnableOrDisEnableEnum.ENABLE.name())")
    AttributeEntity toModel(AttributeDto dto);

    @Mapping(target = "selectValues", expression = "java(Arrays.asList(entity.getSelectValue().split(\",\")))")
    OpsAttributeVo assembleOpsAttributeVo(AttributeEntity entity);

    List<OpsAttributeVo> assembleOpsAttributeVo(List<AttributeEntity> records);
}
