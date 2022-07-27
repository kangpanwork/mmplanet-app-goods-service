package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeValueEntity;
import com.mmplanet.cloud.app.goods.vo.GoodsAttributeValueVo;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoodsAttributeValueTransfer {
    GoodsAttributeValueVo po2vo(GoodsAttributeValueEntity po);
    List<GoodsAttributeValueVo> listPo2listVo(List<GoodsAttributeValueEntity> pos);
}
