package com.mmplanet.cloud.app.goods.controller;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.SkuApi;
import com.mmplanet.cloud.app.goods.application.SkuApplication;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.GoodsDetailSkuVo;
import com.mmplanet.cloud.app.goods.vo.ShoppingCardSkuVo;
import com.mmplanet.cloud.app.goods.vo.SkuVo;
import com.mmplanet.cloud.app.goods.vo.FullSkuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品sku信息表 管理
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Slf4j
@RestController
@RequestMapping("/app/goods/sku")
public class SkuController implements SkuApi {

    @Autowired
    private SkuApplication skuApplication;

    @Override
    public Response<List<SkuVo>> findSkuByParam(Request<FindSkuByParamQueryDto> requestBean) {
        return ResultUtils.construct(skuApplication.findSkuByParam(requestBean.getData()));
    }

    @PostMapping("/V1.0/fastModify")
    public Response<Boolean> fastModify(@Valid @RequestBody Request<FastModifySkuDto> requestBean){
        return ResultUtils.construct(skuApplication.fastModify(requestBean.getData()));
    }

    /**
     * 查询数据
     */
    @Override
    @PostMapping("/V1.0/shoppingcard/list")
    public Response<List<ShoppingCardSkuVo>> shoppingCardSkuList(@Valid @RequestBody Request<ShoppingCardSkusDto> requestBean){
        return ResultUtils.construct(skuApplication.shoppingCardSkuList(requestBean.getData()));
    }

    /**
     * 商品详情SKU查询
     * @param requestBean
     * @return
     */
    @PostMapping("/V1.0/goods/detail")
    @Override
    public Response<GoodsDetailSkuVo> goodsSkuDetail(Request<GoodsDetailSkuDto> requestBean) {
        return ResultUtils.construct(skuApplication.goodsSkuDetail(requestBean.getData()));
    }

    @Override
    public Response<List<FullSkuVo>> wholeDetail(Request<BaseIdsDto> requestBean) {
        return ResultUtils.construct(skuApplication.fullDetail(requestBean.getData()));
    }

    /**
     * 根据id查询
     */
    @Override
    @PostMapping("/V1.0/detail")
    public Response<SkuVo> getById(@Valid @RequestBody Request<BaseIdDto> requestBean){
        BaseIdDto dto = requestBean.getData();
        return ResultUtils.construct(skuApplication.detail(dto.getId()));
    }

    @Override
    public Response<Boolean> stockReduce(Request<SkuStockReduceDto> request) {
        return ResultUtils.construct(skuApplication.stockReduce(request.getData()));
    }
}
