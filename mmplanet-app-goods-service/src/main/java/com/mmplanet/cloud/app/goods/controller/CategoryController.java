package com.mmplanet.cloud.app.goods.controller;

import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.dto.CategoryAttributeQueryDto;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.mmplanet.cloud.app.goods.application.CategoryApplication;


import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryPageQueryDto;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import com.mmplanet.cloud.app.goods.api.CategoryApi;

/**
 * 商品目录表 管理
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Slf4j
@RestController
@RequestMapping("/app/goods/category")
public class CategoryController implements CategoryApi {

    @Autowired
    private CategoryApplication categoryApplication;

    /**
     * 查询分页数据
     */
    @Override
    @PostMapping("/V1.0/page")
    public Response<PageData<CategoryVo>> page(@Valid @RequestBody Request<SearchModel<CategoryPageQueryDto>> requestBean){
        return ResultUtils.construct(categoryApplication.pageQuery(requestBean.getData()));
    }

    @Override
    public Response<List<CategoryTreeVo>> tree() {
        return ResultUtils.construct(categoryApplication.tree());
    }

    /**
     * 查询数据
     */
    @Override
    @PostMapping("/V1.0/list")
    public Response<List<CategoryVo>> list(@Valid @RequestBody Request<CategoryDto> requestBean){
        return ResultUtils.construct(categoryApplication.listFor(requestBean.getData()));
    }
    /**
     * 根据id查询
     */
    @Override
    @PostMapping("/V1.0/detail")
    public Response<CategoryVo> getById(@Valid @RequestBody Request<BaseIdDto> requestBean){
        BaseIdDto dto = requestBean.getData();
        return ResultUtils.construct(categoryApplication.detail(dto.getId()));
    }

    /**
     * 新增
     */
    @Override
    @PostMapping("/V1.0/add")
    public Response<Boolean> add(@Valid @RequestBody Request<CategoryDto> requestBean){
        return ResultUtils.construct(categoryApplication.save(requestBean.getData()));
    }

    /**
     * 修改
    */
    @Override
    @PostMapping("/V1.0/update")
    public Response<Object> update(@Valid @RequestBody Request<CategoryDto> requestBean){
        return ResultUtils.construct(categoryApplication.save(requestBean.getData()));
    }

    @Override
    @PostMapping("/V1.0/attrs")
    public Response<List<AttributeVo>> queryCategoryAttrs(Request<CategoryAttributeQueryDto> requestBean) {
        CategoryAttributeQueryDto dto = requestBean.getData();
        return ResultUtils.construct(categoryApplication.queryCategoryAttrs(dto));
    }

}
