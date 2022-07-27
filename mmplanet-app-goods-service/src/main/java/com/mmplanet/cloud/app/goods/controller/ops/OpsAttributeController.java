package com.mmplanet.cloud.app.goods.controller.ops;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.ops.OpsAttributeApi;
import com.mmplanet.cloud.app.goods.application.AttributeApplication;
import com.mmplanet.cloud.app.goods.dto.AttributeDto;
import com.mmplanet.cloud.app.goods.dto.AttributePageQueryDto;
import com.mmplanet.cloud.app.goods.dto.DeleteAttributeDto;
import com.mmplanet.cloud.app.goods.vo.OpsAttributeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 商品属性表 管理
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Slf4j
@RestController
@RequestMapping("/ops/goods/attribute")
public class OpsAttributeController implements OpsAttributeApi {

    @Autowired
    private AttributeApplication attributeApplication;

    /**
     * 查询分页数据
     */
    @PostMapping("/page")
    public Response<PageData<OpsAttributeVo>> page(@Valid @RequestBody Request<SearchModel<AttributePageQueryDto>> requestBean){
        return ResultUtils.construct(attributeApplication.pageQuery(requestBean.getData()));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Response<Boolean> add(@Valid @RequestBody Request<AttributeDto> requestBean){
        return ResultUtils.construct(StringUtils.isNotEmpty(attributeApplication.save(requestBean.getData())));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Response<Boolean> update(@Valid @RequestBody Request<AttributeDto> requestBean){
        return ResultUtils.construct(StringUtils.isNotEmpty(attributeApplication.save(requestBean.getData())));
    }

    @PostMapping("/detail")
    public Response<OpsAttributeVo> detail(@Valid @RequestBody Request<BaseIdDto> requestBean){
        return ResultUtils.construct(attributeApplication.detail(requestBean.getData().getId()));
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    public Response<Boolean> delete(@Valid @RequestBody Request<DeleteAttributeDto> requestBean){
        return ResultUtils.construct(attributeApplication.delete(requestBean.getData()));
    }
}
