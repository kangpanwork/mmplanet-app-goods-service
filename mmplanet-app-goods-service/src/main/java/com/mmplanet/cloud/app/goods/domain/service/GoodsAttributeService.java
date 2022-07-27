package com.mmplanet.cloud.app.goods.domain.service;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsAttributeRespDto;
import com.mmplanet.cloud.app.goods.dto.GoodsDetailAttributeDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;

public interface GoodsAttributeService extends IService<GoodsAttributeEntity> {


    void saveGoodsAttribute(GoodsEntity goodsEntity, List<GoodsDetailAttributeDto> attributeList);

    List<GoodsAttributeRespDto> queryGoodsAttribute(String goodsId);

    void deleteGoodsAttribute(String goodsId);
}
