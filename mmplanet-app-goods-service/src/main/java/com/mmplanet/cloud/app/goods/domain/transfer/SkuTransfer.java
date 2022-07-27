package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.application.dto.resp.SkuRespDto;
import com.mmplanet.cloud.app.goods.dto.SkuDto;
import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;
import com.mmplanet.cloud.app.goods.vo.FullSkuVo;
import com.mmplanet.cloud.app.goods.vo.GoodsDetailSkuVo;
import com.mmplanet.cloud.app.goods.vo.SkuVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkuTransfer {
    SkuVo assembleSkuVo(SkuEntity skuEntity);
    GoodsDetailSkuVo assembleGoodsDetailSkuVo(SkuEntity skuEntity);
    FullSkuVo assembleFullSkuVo(SkuEntity e);



    SkuEntity toModel(SkuDto e);

    List<SkuRespDto> convertSkuRespDto(List<SkuEntity> list);
}
