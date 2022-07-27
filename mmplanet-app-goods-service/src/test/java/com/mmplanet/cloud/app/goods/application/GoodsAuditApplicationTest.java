package com.mmplanet.cloud.app.goods.application;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsDealerGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.application.fixed.DealerShopFactory;
import com.mmplanet.cloud.app.goods.application.fixed.DealerUserFactory;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.dto.GoodsAuditDto;
import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import com.mmplanet.cloud.app.goods.dto.GoodsManageAuditDetailQueryDto;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditDetailVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/8 11:43 <br>
 * @Author: niujiao
 */
public class GoodsAuditApplicationTest extends BaseTest{

    @Autowired
    private GoodsAuditApplication goodsAuditApplication;

    @MockBean
    private GenericService genericService;

    @Test
    public void should_return_success_when_page_given_noArgument() {

        when(genericService.getGenericShopInfoByShopId(anyString(),anyString(),anyBoolean(),anyBoolean()))
                .thenReturn(DealerShopFactory.build());

        SearchModel<OpsGoodsAuditListQueryDto> searchModel = new SearchModel<>();
        OpsGoodsAuditListQueryDto query = new OpsGoodsAuditListQueryDto();
        query.setAuditStatus("PASS");
        searchModel.setModel(query);

        PageData<GoodsManageAuditListVo> pageData = goodsAuditApplication.opsAuditPage(searchModel);

        Assert.assertNotNull(pageData);

        Assert.assertEquals(pageData.getList().get(0).getHandleNode(),"OPS");
    }

    @Test
    public void should_return_success_when_dealerPage_given_noArgument() {

        when(genericService.getGenericShopInfoByShopId(anyString(),anyString(),anyBoolean(),anyBoolean()))
                .thenReturn(DealerShopFactory.build());

        SearchModel<OpsDealerGoodsAuditListQueryDto> searchModel = new SearchModel<>();
        OpsDealerGoodsAuditListQueryDto query = new OpsDealerGoodsAuditListQueryDto();
        query.setAuditStatus("PASS");
        searchModel.setModel(query);

        PageData<GoodsManageAuditListVo> pageData = goodsAuditApplication.dealerAuditPage(searchModel);

        Assert.assertNotNull(pageData);

        Assert.assertEquals(pageData.getList().get(0).getHandleNode(),"DEALER");
    }

    @Test
    public void should_return_success_when_create_given_priGoodsType() throws IOException {
        when(genericService.getUser(any())).thenReturn(DealerUserFactory.build());
        when(genericService.getGenericShopInfoByUserId(anyString(),anyString(),anyBoolean(),anyBoolean()))
                .thenReturn(DealerShopFactory.build());

        ClassPathResource jsonDataResource = new ClassPathResource("pri_goods_create.json");
        GoodsDto dto = JSON.parseObject(jsonDataResource.getInputStream(), GoodsDto.class);


        String id = goodsAuditApplication.save(dto);
        Assert.assertNotNull(id);
    }

    @Test
    public void should_return_success_when_create_given_sellWithDhGoodsType() throws IOException {
        when(genericService.getUser(any())).thenReturn(DealerUserFactory.build());
        when(genericService.getGenericShopInfoByUserId(anyString(),anyString(),anyBoolean(),anyBoolean()))
                .thenReturn(DealerShopFactory.build());

        ClassPathResource jsonDataResource = new ClassPathResource("sell_dh_goods_create.json");
        GoodsDto dto = JSON.parseObject(jsonDataResource.getInputStream(), GoodsDto.class);


        String id = goodsAuditApplication.save(dto);
        Assert.assertNotNull(id);
    }

    @Test
    public void should_return_success_when_audit_given_priGoodsType() throws IOException {
        GoodsAuditDto dto = new GoodsAuditDto();
        dto.setId("8b86ee98df22322023fc877979e3e840");
        dto.setAuditStatus("PASS");
        dto.setHandleNode("OPS");
        dto.setAuditUser("ops");
        Boolean result = goodsAuditApplication.opsAudit(dto);
        Assert.assertNotNull(result);

        GoodsManageAuditDetailQueryDto queryDto = new GoodsManageAuditDetailQueryDto();
        queryDto.setId("8b86ee98df22322023fc877979e3e840");
        GoodsManageAuditDetailVo detailVo = goodsAuditApplication.manageAuditDetail(queryDto);
        Assert.assertEquals(detailVo.getStatus(),"PASS");
    }
}