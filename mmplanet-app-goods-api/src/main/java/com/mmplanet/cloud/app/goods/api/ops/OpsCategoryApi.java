package com.mmplanet.cloud.app.goods.api.ops;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/30 18:31 <br>
 * @Author: niujiao
 */
@FeignClient(path = "/ops/goods/category", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface OpsCategoryApi {

    /**
     * 查询分页数据
     */
    @PostMapping("/page")
    Response<PageData<CategoryVo>> page(@Valid @RequestBody Request<SearchModel<CategoryPageQueryDto>> requestBean);

    @PostMapping("/tree")
    Response<List<CategoryTreeVo>> tree();


    @PostMapping("/detail")
    Response<CategoryVo> detail(@Valid @RequestBody Request<BaseIdDto> requestBean);

    /**
     * 新增
     */
    @PostMapping("/add")
    Response<Boolean> add(@Valid @RequestBody Request<CategoryDto> requestBean);

    /**
     * 修改
     */
    @PostMapping("/update")
    Response<Boolean> update(@Valid @RequestBody Request<CategoryDto> requestBean);

    /**
     * 删除
     */
    @PostMapping("/del")
    Response<Boolean> delete(@Valid @RequestBody Request<DeleteCategoryDto> requestBean);

    /**
     * 关联属性
     * @param requestBean
     * @return
     */
    @PostMapping("/associateAttr")
    Response<Boolean> associateAttr(@Valid @RequestBody Request<AssociateAttrDto> requestBean);

    /**
     * 获取类目下所有属性
     * @param requestBean
     * @return
     */
    @PostMapping("/V1.0/attrs")
    Response<List<AttributeVo>>queryCategoryAttrs(@Valid @RequestBody Request<CategoryAttributeQueryDto> requestBean);
}
