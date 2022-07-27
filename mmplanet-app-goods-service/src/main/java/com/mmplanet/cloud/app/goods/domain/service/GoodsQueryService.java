package com.mmplanet.cloud.app.goods.domain.service;

import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManagePageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsPageQuery;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsListRespDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;

import java.util.function.Consumer;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/23 11:46 <br>
 * @Author: niujiao
 */
public interface GoodsQueryService {

    /**
     * 商品搜索
     *
     * @param pageNum
     * @param pageSize
     * @param query
     * @return
     */
    PageData<GoodsListRespDto> pageQuery(Integer pageNum, Integer pageSize, GoodsPageQuery query);

    /**
     * 店铺首页列表
     *
     * @param pageNum
     * @param pageSize
     * @param query
     * @return
     */
    PageData<GoodsListRespDto> shopHomePage(Integer pageNum, Integer pageSize, GoodsPageQuery query);

    /**
     * 商品管理
     *
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    PageData<GoodsListRespDto> managePageQuery(Integer pageNum, Integer pageSize, GoodsManagePageQuery model);

    /**
     * @param id 商品id
     * @return
     */
    GoodsDetailRespDto detail(String id, Consumer<GoodsEntity> consume);

    GoodsDetailRespDto manageDetail(GoodsManageQuery query);
}
