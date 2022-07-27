package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;

import java.util.List;

public interface SkuService extends IService<SkuEntity> {

    List<SkuEntity> get(List<String> skuIds);

    List<SkuEntity> findByGoodsId(String goodsId);

    List<SkuEntity> findByGoodsId(String goodsId, Boolean isPurchase, Boolean deleted);

    boolean reduceStock(String skuId, Integer num);

    boolean addStock(String skuId, Integer num);

    void save(List<SkuEntity> skuEntities);

}
