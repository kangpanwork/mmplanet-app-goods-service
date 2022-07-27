package com.mmplanet.cloud.app.goods.api;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.FullSkuVo;
import com.mmplanet.cloud.app.goods.vo.GoodsDetailSkuVo;
import com.mmplanet.cloud.app.goods.vo.ShoppingCardSkuVo;
import com.mmplanet.cloud.app.goods.vo.SkuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品sku信息表 接口提供
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@FeignClient(path = "/app/goods/sku", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface SkuApi {

    @PostMapping("/findSkuByParam")
    Response<List<SkuVo>> findSkuByParam(@Valid @RequestBody Request<FindSkuByParamQueryDto> request);

    /**
     * 修改SKU价格和库存
     * @param requestBean
     * @return
     */
    @PostMapping("/V1.0/fastModify")
    Response<Boolean> fastModify(@Valid @RequestBody Request<FastModifySkuDto> requestBean);

    /**
     * 查询数据
     */
    @PostMapping("/V1.0/shoppingcard/list")
    Response<List<ShoppingCardSkuVo>> shoppingCardSkuList(@Valid @RequestBody Request<ShoppingCardSkusDto> requestBean);

    @PostMapping("/V1.0/goods/detail")
    Response<GoodsDetailSkuVo> goodsSkuDetail(@Valid @RequestBody Request<GoodsDetailSkuDto> requestBean);

    /**
     * SKU详情 for internal
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/wholeDetail")
    Response<List<FullSkuVo>> wholeDetail(@Valid @RequestBody Request<BaseIdsDto> requestBean);

    /**
     * 根据id查询
     */
    @PostMapping("/V1.0/detail")
    Response<SkuVo> getById(@Valid @RequestBody Request<BaseIdDto> requestBean);

    @PostMapping("V1.0/stock/reduce")
    Response<Boolean> stockReduce(@Valid @RequestBody Request<SkuStockReduceDto> request);
}
