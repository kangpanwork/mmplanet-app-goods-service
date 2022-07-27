package com.mmplanet.cloud.app.goods.application;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.application.fixed.SmallBShopFactory;
import com.mmplanet.cloud.app.goods.application.fixed.SmallBUserFactory;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.dto.CalFreightV2Dto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateDto;
import com.mmplanet.cloud.app.goods.dto.FreightTemplateQueryDto;
import com.mmplanet.cloud.app.goods.vo.CalFreightV2Vo;
import com.mmplanet.cloud.app.goods.vo.FreightTemplateListVo;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 13:27 <br>
 * @Author: niujiao
 */
public class FreightApplicationTest extends BaseTest{

    @Autowired
    private FreightApplication freightApplication;

    @MockBean
    private GenericService genericService;

    @Test
    public void create() throws IOException {
        when(genericService.getUser(any())).thenReturn(SmallBUserFactory.build());
        when(genericService.getGenericShopInfoByUserId(anyString(),anyString(),anyBoolean(),anyBoolean()))
                .thenReturn(SmallBShopFactory.build());

        ClassPathResource jsonDataResource = new ClassPathResource("freight_template_add.json");
        String jsonDataText = IOUtils.toString(jsonDataResource.getInputStream(), "utf-8");
        FreightTemplateDto dto = JSON.parseObject(jsonDataText, FreightTemplateDto.class);
        String id = freightApplication.save(dto);
        Assert.notEmpty(id);
    }

    @Test
    public void page() {
        when(genericService.getUser(any())).thenReturn(SmallBUserFactory.build());
        when(genericService.getGenericShopInfoByUserId(anyString(),anyString(),anyBoolean(),anyBoolean()))
                .thenReturn(SmallBShopFactory.build());

        FreightTemplateQueryDto dto = new FreightTemplateQueryDto();
        SearchModel<FreightTemplateQueryDto> searchQuery = new SearchModel<FreightTemplateQueryDto>();
        dto.setType("PREPAYMENT");
        dto.setUserId("");
        searchQuery.setModel(dto);
        PageData<FreightTemplateListVo> page = freightApplication.page(searchQuery);
        Assert.isTrue(page.getPage().getTotal() == 1);
    }

    @Test
    public void calFreightV2() throws IOException {

        ClassPathResource jsonDataResource = new ClassPathResource("goods_cal_freight.json");
        String jsonDataText = IOUtils.toString(jsonDataResource.getInputStream(), StandardCharsets.UTF_8);
        CalFreightV2Dto dto = JSON.parseObject(jsonDataText, CalFreightV2Dto.class);
        CalFreightV2Vo result = freightApplication.calFreightV2(dto);
        System.out.println(result);
    }
}