package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.infra.entity.CategoryAttributeEntity;
import com.mmplanet.cloud.app.goods.vo.CategoryAttributeVo;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryAttributeTransfer {
    CategoryAttributeVo po2vo(CategoryAttributeEntity po);
    List<CategoryAttributeVo> listPo2listVo(List<CategoryAttributeEntity> pos);
}
