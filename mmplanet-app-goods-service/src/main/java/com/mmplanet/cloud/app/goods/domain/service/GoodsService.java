package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.goods.dto.GoodsManageStatisticsQueryDto;
import com.mmplanet.cloud.app.goods.dto.GoodsManageTopDto;
import com.mmplanet.cloud.app.goods.dto.GoodsUpAndDownDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.vo.GoodsManageStatisticsVo;

import java.util.List;

public interface GoodsService extends IService<GoodsEntity> {

    GoodsEntity getById(String id);

    List<GoodsEntity> listByIds(List<String> ids);

    Boolean manageUpAndDown(GoodsUpAndDownDto requestBean);

    Boolean removeByIds(List<String> ids, String opUserId);

    /**
     * 置顶
     * @param requestBean
     * @return
     */
    Boolean manageTop(GoodsManageTopDto requestBean);

    GoodsManageStatisticsVo manageStatistics(GoodsManageStatisticsQueryDto requestBean);

    boolean reduceSaleNum(String goodsId, Integer num);

    boolean addSaleNum(String goodsId, Integer num);

    boolean reduceStockNum(String goodsId, Integer num);

    boolean addStockNum(String goodsId, Integer num);
}
