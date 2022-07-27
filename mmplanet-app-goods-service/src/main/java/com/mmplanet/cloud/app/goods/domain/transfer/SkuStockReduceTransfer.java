package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.dto.SkuStockReduceDto;
import com.mmplanet.cloud.app.goods.infra.entity.SkuStockReduceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkuStockReduceTransfer {

    SkuStockReduceEntity convert(SkuStockReduceDto data);
}
