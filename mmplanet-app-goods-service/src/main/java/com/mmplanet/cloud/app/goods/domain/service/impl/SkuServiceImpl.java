package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.service.SkuService;
import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.SkuMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    @Override
    public List<SkuEntity> get(List<String> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            throw new NullPointerException("skuIds is required");
        }
        return getBaseMapper().get(skuIds);
    }


    @Override
    public List<SkuEntity> findByGoodsId(String goodsId) {
        if (StringUtils.isEmpty(goodsId)) {
            throw new NullPointerException("goodsId is required");
        }
        return findByGoodsId(goodsId, true, false);
    }

    @Override
    public List<SkuEntity> findByGoodsId(String goodsId, Boolean isPurchase, Boolean deleted) {
        if (StringUtils.isEmpty(goodsId)) {
            throw new NullPointerException("goodsId is required");
        }
        return getBaseMapper().findByGoodsId(goodsId, isPurchase, deleted);
    }

    @Override
    public boolean reduceStock(String skuId, Integer num) {
        return getBaseMapper().stockReduce(skuId, num) == 1;
    }

    @Override
    public boolean addStock(String skuId, Integer num) {
        return getBaseMapper().stockAdd(skuId, num) == 1;
    }

    @Override
    public void save(List<SkuEntity> skuEntities) {
        if (CollectionUtils.isEmpty(skuEntities)) {
            throw new NullPointerException("skuEntities is required");
        }
        skuEntities.forEach(e -> {
            if (StringUtils.isNotEmpty(e.getId())) {
                getBaseMapper().updateById(e);
            } else {
                getBaseMapper().insert(e);
            }
        });
    }
}



