package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.goods.dto.SkuStockReduceDto;
import com.mmplanet.cloud.app.goods.infra.entity.SkuStockReduceEntity;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 16:25 <br>
 * @Author: niujiao
 */
public interface SkuStockReduceService extends IService<SkuStockReduceEntity> {

    boolean save(SkuStockReduceDto data);
}
