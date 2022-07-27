package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeEntity;
import com.mmplanet.cloud.app.goods.vo.GoodsAttributeVo;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoodsAttributeTransfer {
    GoodsAttributeVo po2vo(GoodsAttributeEntity po);
    List<GoodsAttributeVo> listPo2listVo(List<GoodsAttributeEntity> pos);
}
