package com.mmplanet.cloud.app.goods.api;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.dto.CategoryAttributeQueryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryPageQueryDto;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品目录表 接口提供
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@FeignClient(path = "/app/goods/category", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface CategoryApi {


    /**
       * 查询分页数据
       */
    @PostMapping("/V1.0/page")
    Response<PageData<CategoryVo>> page(@Valid @RequestBody Request<SearchModel<CategoryPageQueryDto>> requestBean);

    @PostMapping("/V1.0/tree")
    Response<List<CategoryTreeVo>> tree();
    /**
     * 查询数据
     */
    @PostMapping("/V1.0/list")
    Response<List<CategoryVo>> list(@Valid @RequestBody Request<CategoryDto> requestBean);

    /**
     * 根据id查询
     */
    @PostMapping("/V1.0/detail")
    Response<CategoryVo> getById(@Valid @RequestBody Request<BaseIdDto> requestBean);

    /**
     * 新增
     */
    @PostMapping("/V1.0/add")
    Response<Boolean> add(@Valid @RequestBody Request<CategoryDto> requestBean);

    /**
     * 修改
    */
    @PostMapping("/V1.0/update")
    Response<Object> update(@Valid @RequestBody Request<CategoryDto> requestBean);

    /**
     * 查询数据
     */
    @PostMapping("/V1.0/attrs")
    Response<List<AttributeVo>> queryCategoryAttrs(@Valid @RequestBody Request<CategoryAttributeQueryDto> query);

}
