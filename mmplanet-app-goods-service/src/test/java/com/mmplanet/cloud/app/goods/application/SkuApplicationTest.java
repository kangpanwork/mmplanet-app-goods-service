package com.mmplanet.cloud.app.goods.application;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.goods.dto.SkuStockReduceDto;
import com.mmplanet.cloud.app.goods.vo.FullSkuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/27 19:16 <br>
 * @Author: niujiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuApplicationTest {

    @Autowired
    SkuApplication skuApplication;

    @Test
    public void fullDetail() {
        BaseIdsDto dto = new BaseIdsDto();
        dto.setIds(Lists.newArrayList("e3e0cbf6d229e33ecccef1773b3cdc8b"));
        List<FullSkuVo> fullSkuVos = skuApplication.fullDetail(dto);
        Assert.notEmpty(fullSkuVos);
    }

    @Test
    public void stockReduce() {
        SkuStockReduceDto data = new SkuStockReduceDto();
        data.setSkuId("dbfc0ecc683a76664e4d1a9160c5f1d4");
        data.setBusinessId("22070400180000509");
        data.setNum(1);
        data.setType("REDUCE");
        data.setBusinessType("ORDER");
        Boolean result = skuApplication.stockReduce(data);
        Assert.isTrue(result);
    }
}