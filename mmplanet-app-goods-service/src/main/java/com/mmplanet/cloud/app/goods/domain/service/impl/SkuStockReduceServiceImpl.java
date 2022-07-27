package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.service.SkuStockReduceService;
import com.mmplanet.cloud.app.goods.domain.transfer.SkuStockReduceTransfer;
import com.mmplanet.cloud.app.goods.dto.SkuStockReduceDto;
import com.mmplanet.cloud.app.goods.infra.entity.SkuStockReduceEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.SkuStockReduceMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 16:25 <br>
 * @Author: niujiao
 */
@Service
public class SkuStockReduceServiceImpl extends ServiceImpl<SkuStockReduceMapper, SkuStockReduceEntity> implements SkuStockReduceService {

    @Resource
    private SkuStockReduceTransfer skuStockReduceTransfer;

    @Override
    public boolean save(SkuStockReduceDto data) {
        SkuStockReduceEntity entity = skuStockReduceTransfer.convert(data);
        try {
            save(entity);
        } catch (DuplicateKeyException ignore) {
            return false;
        }
        return true;
    }
}
