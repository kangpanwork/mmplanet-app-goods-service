package com.mmplanet.cloud.app.goods.controller;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.FreightApi;
import com.mmplanet.cloud.app.goods.application.FreightApplication;
import com.mmplanet.cloud.app.goods.dto.CalFreightDto;
import com.mmplanet.cloud.app.goods.dto.CalFreightV2Dto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateDto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateQueryDto;
import com.mmplanet.cloud.app.goods.vo.CalFreightV2Vo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateListVo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 14:43 <br>
 * @Author: niujiao
 */
@Slf4j
@RestController
@RequestMapping("/app/freight/")
public class FreightController implements FreightApi {

    @Autowired
    private FreightApplication freightApplication;

    @PostMapping("/createTemplate")
    @Override
    public Response<String> createTemplate(@Valid @RequestBody Request<FreightTemplateDto> request) {
        return ResultUtils.construct(freightApplication.save(request.getData()));
    }


    @PostMapping("/templateDetail")
    public Response<FreightTemplateVo> templateDetail(@Valid @RequestBody Request<BaseIdDto> request){
        return ResultUtils.construct(freightApplication.detail(request.getData().getId()));
    }


    /**
     * 删除模板
     *
     * @param request
     * @return
     */
    public Response<Boolean> del(@Valid @RequestBody Request<BaseIdsDto> request){
        return ResultUtils.construct(freightApplication.del(request.getData().getIds()));
    }

    /**
     * 删除模板规则
     * @param request
     * @return
     */
    public Response<Boolean> delTemplateRule(@Valid @RequestBody Request<BaseIdsDto> request){
        return ResultUtils.construct(freightApplication.delTemplateRule(request.getData().getIds()));
    }

    @PostMapping("/templatePage")
    @Override
    public Response<PageData<FreightTemplateListVo>> templatePage(@Valid @RequestBody Request<SearchModel<FreightTemplateQueryDto>> request) {
        return ResultUtils.construct(freightApplication.page(request.getData()));
    }

    @PostMapping("/calFreight")
    @Override
    public Response<Integer> calFreight(@Valid @RequestBody Request<CalFreightDto> request) {
        return ResultUtils.construct(freightApplication.calFreight(request.getData()));
    }

    @PostMapping("/calFreightV2")
    public Response<CalFreightV2Vo> calFreightV2(@Valid @RequestBody Request<CalFreightV2Dto> request){
        return ResultUtils.construct(freightApplication.calFreightV2(request.getData()));
    }
}
