package com.mmplanet.cloud.app.goods.api.ops;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.dto.AttributeDto;
import com.mmplanet.cloud.app.goods.dto.AttributePageQueryDto;
import com.mmplanet.cloud.app.goods.dto.DeleteAttributeDto;
import com.mmplanet.cloud.app.goods.vo.OpsAttributeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/30 18:20 <br>
 * @Author: niujiao
 */
@FeignClient(path = "/ops/goods/attribute", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface OpsAttributeApi {

    /**
     * 查询分页数据
     */
    @PostMapping("/page")
    Response<PageData<OpsAttributeVo>> page(@Valid @RequestBody Request<SearchModel<AttributePageQueryDto>> requestBean);

    /**
     * 新增
     */
    @PostMapping("/add")
    Response<Boolean> add(@Valid @RequestBody Request<AttributeDto> requestBean);

    /**
     * 修改
     */
    @PostMapping("/update")
    Response<Boolean> update(@Valid @RequestBody Request<AttributeDto> requestBean);

    /**
     * 删除
     */
    @PostMapping("/del")
    Response<Boolean> delete(@Valid @RequestBody Request<DeleteAttributeDto> requestBean);

    /**
     * 详情
     * @param requestBean
     * @return
     */
    @PostMapping("/detail")
    Response<OpsAttributeVo> detail(@Valid @RequestBody Request<BaseIdDto> requestBean);

}
