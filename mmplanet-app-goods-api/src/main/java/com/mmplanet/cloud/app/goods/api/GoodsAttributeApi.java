package com.mmplanet.cloud.app.goods.api;

import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import org.springframework.cloud.openfeign.FeignClient;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import com.mmplanet.cloud.app.common.response.Response;
import java.util.List;

import com.mmplanet.cloud.app.goods.dto.GoodsAttributeDto;
import com.mmplanet.cloud.app.goods.dto.GoodsAttributePageQueryDto;
import com.mmplanet.cloud.app.goods.vo.GoodsAttributeVo;

/**
 * 商品下详细属性 接口提供
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@FeignClient(path = "/app/goods/goodsAttribute", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface GoodsAttributeApi {

    /**
       * 查询分页数据
       */
    @PostMapping("/V1.0/page")
    Response<PageData<GoodsAttributeVo>> page(@Valid @RequestBody Request<SearchModel<GoodsAttributePageQueryDto>> requestBean);

    /**
     * 查询数据
     */
    @PostMapping("/V1.0/list")
    Response<List<GoodsAttributeVo>> list(@Valid @RequestBody Request<GoodsAttributeDto> requestBean);

    /**
     * 根据id查询
     */
    @PostMapping("/V1.0/detail")
    Response<GoodsAttributeVo> getById(@Valid @RequestBody Request<BaseIdDto> requestBean);

    /**
     * 新增
     */
    @PostMapping("/V1.0/add")
    Response<Boolean> add(@Valid @RequestBody Request<GoodsAttributeDto> requestBean);

    /**
     * 修改
    */
    @PostMapping("/V1.0/update")
    Response<Object> update(@Valid @RequestBody Request<GoodsAttributeDto> requestBean);

    /**
     * 删除
     */
    @PostMapping("/V1.0/del")
    Response<Boolean> delete(@Valid @RequestBody Request<BaseIdsDto> requestBean);

}
