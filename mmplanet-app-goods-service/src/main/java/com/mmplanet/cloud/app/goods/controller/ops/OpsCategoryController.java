package com.mmplanet.cloud.app.goods.controller.ops;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.ops.OpsCategoryApi;
import com.mmplanet.cloud.app.goods.application.CategoryApplication;
import com.mmplanet.cloud.app.goods.application.CategoryAttributeApplication;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/30 18:47 <br>
 * @Author: niujiao
 */
@Slf4j
@RestController
@RequestMapping("/ops/goods/category")
public class OpsCategoryController implements OpsCategoryApi {

    @Autowired
    private CategoryApplication categoryApplication;

    @Autowired
    private CategoryAttributeApplication categoryAttributeApplication;

    /**
     * 查询分页数据
     */
    @PostMapping("/page")
    public Response<PageData<CategoryVo>> page(@Valid @RequestBody Request<SearchModel<CategoryPageQueryDto>> requestBean){
        return ResultUtils.construct(categoryApplication.pageQuery(requestBean.getData()));
    }

    @PostMapping("/tree")
    public Response<List<CategoryTreeVo>> tree(){
        return ResultUtils.construct(categoryApplication.tree());
    }

    @PostMapping("/detail")
    @Override
    public Response<CategoryVo> detail(Request<BaseIdDto> requestBean) {
        return ResultUtils.construct(categoryApplication.detail(requestBean.getData().getId()));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Response<Boolean> add(@Valid @RequestBody Request<CategoryDto> requestBean){
        return ResultUtils.construct(categoryApplication.save(requestBean.getData()));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Response<Boolean> update(@Valid @RequestBody Request<CategoryDto> requestBean){
        return ResultUtils.construct(categoryApplication.save(requestBean.getData()));
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    public Response<Boolean> delete(@Valid @RequestBody Request<DeleteCategoryDto> requestBean){
        return ResultUtils.construct(categoryApplication.delete(requestBean.getData()));
    }

    /**
     * 关联属性
     * @param requestBean
     * @return
     */
    @PostMapping("/associateAttr")
    public Response<Boolean> associateAttr(@Valid @RequestBody Request<AssociateAttrDto> requestBean){
        return ResultUtils.construct(categoryAttributeApplication.save(requestBean.getData()));
    }

    @PostMapping("/V1.0/attrs")
    @Override
    public Response<List<AttributeVo>> queryCategoryAttrs(Request<CategoryAttributeQueryDto> requestBean) {
        return ResultUtils.construct(categoryApplication.queryCategoryAttrs(requestBean.getData()));
    }
}
