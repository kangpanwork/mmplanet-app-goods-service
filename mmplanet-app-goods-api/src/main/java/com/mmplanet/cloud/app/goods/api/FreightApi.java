package com.mmplanet.cloud.app.goods.api;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.dto.CalFreightDto;
import com.mmplanet.cloud.app.goods.dto.CalFreightV2Dto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateDto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateQueryDto;
import com.mmplanet.cloud.app.goods.vo.CalFreightV2Vo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateListVo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 运费模板API
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 14:31 <br>
 * @Author: niujiao
 */
@FeignClient(path = "/app/freight/", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface FreightApi {

    /**
     * 创建模板
     * @param request
     * @return
     */
    @PostMapping("/createTemplate")
    Response<String> createTemplate(@Valid @RequestBody Request<FreightTemplateDto> request);


    @PostMapping("/templateDetail")
    Response<FreightTemplateVo> templateDetail(@Valid @RequestBody Request<BaseIdDto> request);

    /**
     * 删除模板
     *
     * @param request
     * @return
     */
    @PostMapping("/del")
    Response<Boolean> del(@Valid @RequestBody Request<BaseIdsDto> request);

    /**
     * 删除模板规则
     * @param request
     * @return
     */
    @PostMapping("/delTemplateRule")
    Response<Boolean> delTemplateRule(@Valid @RequestBody Request<BaseIdsDto> request);
    /**
     * 模板分页查询模板
     * @return
     */
    @PostMapping("/templatePage")
    Response<PageData<FreightTemplateListVo>> templatePage(@Valid @RequestBody Request<SearchModel<FreightTemplateQueryDto>> request);

    /**
     * 计算运费
     * @param request
     * @return 返回分
     */
    @PostMapping("/calFreight")
    Response<Integer> calFreight(@Valid @RequestBody Request<CalFreightDto> request);

    @PostMapping("/calFreightV2")
    Response<CalFreightV2Vo> calFreightV2(@Valid @RequestBody Request<CalFreightV2Dto> request);
}
