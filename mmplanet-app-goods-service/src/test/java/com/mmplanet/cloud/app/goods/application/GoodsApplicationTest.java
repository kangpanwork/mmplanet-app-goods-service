//package com.mmplanet.cloud.app.goods.application;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.google.common.collect.Lists;
//import com.mmplanet.cloud.app.common.dto.BaseIdDto;
//import com.mmplanet.cloud.app.common.page.Page;
//import com.mmplanet.cloud.app.common.page.PageData;
//import com.mmplanet.cloud.app.common.request.SearchModel;
//import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsAuditListQueryDto;
//import com.mmplanet.cloud.app.goods.application.fixed.DealerUserFactory;
//import com.mmplanet.cloud.app.goods.application.fixed.SmallBShopFactory;
//import com.mmplanet.cloud.app.goods.domain.service.GenericService;
//import com.mmplanet.cloud.app.goods.dto.*;
//import com.mmplanet.cloud.app.goods.infra.integration.ShoppingCardApiClient;
//import com.mmplanet.cloud.app.goods.infra.integration.UserBlacklistApiClient;
//import com.mmplanet.cloud.app.goods.vo.GoodsDetailVo;
//import com.mmplanet.cloud.app.goods.vo.GoodsListVo;
//import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
//import com.mmplanet.cloud.app.goods.vo.GoodsManageListVo;
//import com.mmplanet.cloud.app.user.dto.UserBlacklistQueryDto;
//import com.mmplanet.cloud.app.user.vo.UserBlacklistVo;
//import org.apache.commons.io.IOUtils;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
///**
// * @Company: chaohuhu © Copyright 2022<br>
// * @Description: <br>
// * @Project: mmplanet <br>
// * @CreateDate: Created in 2022/5/21 14:13 <br>
// * @Author: niujiao
// */
//public class GoodsApplicationTest extends BaseTest{
//
//    @MockBean
//    private GenericService genericService;
//
//    @MockBean
//    private ShoppingCardApiClient shoppingCardApiClient;
//
//    @Resource
//    GoodsApplication goodsApplication;
//
//    @Autowired
//    private GoodsAuditApplication goodsAuditApplication;
//
//    @MockBean
//    private UserBlacklistApiClient userBlacklistApiClient;
//
//    @Test
//    public void add() throws IOException {
//        when(genericService.getUser(any())).thenReturn(DealerUserFactory.build());
//        when(genericService.getGenericShopInfoByUserId(anyString(),anyString(),anyBoolean(),anyBoolean()))
//                .thenReturn(SmallBShopFactory.build());
//
//        ClassPathResource jsonDataResource = new ClassPathResource("goods_add.json");
//        GoodsAuditReqDto dto = JSON.parseObject(jsonDataResource.getInputStream(), GoodsAuditReqDto.class);
//        String id = goodsAuditApplication.submitApply(dto);
//        Assert.assertNotNull(id);
//
//        //GoodsAuditDto auditDto = new GoodsAuditDto();
//        //auditDto.setId(id);
//        //auditDto.setAuditStatus(GoodsAuditStatusEnum.PASS.name());
//        //Boolean result = goodsAuditApplication.audit(auditDto);
//        //Assert.assertTrue(result);
//    }
//
//
//    @Test
//    public void manageDelete(){
//        GoodsManageDeleteDto request = new GoodsManageDeleteDto();
//        request.setGoodsIds(Lists.newArrayList("df279e534ac8810f5690f2b1ae2341c7"));
//        request.setOpUserId("system");
//        Boolean result = goodsApplication.manageDelete(request);
//        Assert.assertTrue(result);
//    }
//
//   @Transactional
//   @Rollback(false)
//    @Test
//    public void audit(){
//       String[] goodIds = {"14cf88a451b712fc3af7a37ce72196a0"};
//        for(String goodsId : goodIds){
//            GoodsAuditDto data = new GoodsAuditDto();
//            data.setId(goodsId);
//            data.setAuditStatus("PASS");
//            data.setAuditUser("system");
//            data.setRejectReason("审核通过");
//            Boolean result = goodsAuditApplication.opsAudit(data);
//            Assert.assertTrue(result);
//        }
//    }
//
//    @Test
//    public void managePage() throws IOException {
//
//        when(shoppingCardApiClient.goodsPurchaseNum(any())).thenReturn(new ArrayList<>());
//        when(genericService.getGenericShopInfoByShopId(anyString(),anyString(),anyBoolean(),anyBoolean()))
//                .thenReturn(SmallBShopFactory.build());
//
//        ClassPathResource jsonDataResource = new ClassPathResource("manage_page.json");
//        String jsonData = IOUtils.toString(jsonDataResource.getInputStream(),"utf-8");
//        SearchModel<GoodsManagePageQuery> searchModel = JSON.parseObject(jsonData, new TypeReference<SearchModel<GoodsManagePageQuery>>() {
//        });
//        PageData<GoodsManageListVo> pageDate = goodsApplication.managePage(searchModel);
//        Assert.assertNotNull(pageDate);
//    }
//
//    @Test
//    public void detail(){
//        BaseIdDto idDto = new BaseIdDto();
//        idDto.setId("465d3d6d6855993db752a22ca1c2f67f");
//        GoodsDetailVo goodsDetailVo = goodsApplication.detail(idDto);
//        System.out.println(JSON.toJSON(goodsDetailVo));
//    }
//
//    @Test
//    public void opsAuditPage(){
//        when(genericService.getGenericShopInfoByShopId(anyString(),anyString(),anyBoolean(),anyBoolean()))
//                .thenReturn(SmallBShopFactory.build());
//
//        SearchModel<OpsGoodsAuditListQueryDto> searchModel = new SearchModel<OpsGoodsAuditListQueryDto>();
//        OpsGoodsAuditListQueryDto dto = new OpsGoodsAuditListQueryDto();
//        dto.setAuditStatus("TO_AUDIT");
//        searchModel.setModel(dto);
//        PageData<GoodsManageAuditListVo> pageData = goodsAuditApplication.opsAuditPage(searchModel);
//        Assert.assertTrue(pageData.getPage().getTotal() >= 0);
//    }
//
//    @Test
//    public void pageQuery(){
//
//        String jsonData = "{\"page\":{\"pageNum\":1,\"pageSize\":10,\"total\":1,\"hasNextPage\":false,\"nextPage\":null},\"list\":[{\"userId\":\"971607ac557f410c935a0a4b62dc06d0\",\"businessType\":\"GOODS\",\"businessId\":\"8bec36e9f2ec3c94c54e6fde7159ac97\"}],\"other\":null}";
//        PageData<UserBlacklistVo> pageData = JSON.parseObject(jsonData, new TypeReference<PageData<UserBlacklistVo>>() {
//        });
//        when(userBlacklistApiClient.page(anyInt(),anyInt(),any(UserBlacklistQueryDto.class))).thenReturn(pageData);
//
//        SearchModel<GoodsPageQuery> searchModel = new SearchModel<>();
//        GoodsPageQuery model = new GoodsPageQuery();
//        model.setUserId("971607ac557f410c935a0a4b62dc06d0");
//        searchModel.setModel(model);
//        Page page = new Page();
//        page.setPageNum(1);
//        page.setPageSize(10);
//        searchModel.setPage(page);
//        PageData<GoodsListVo> goodsListVoPageData = goodsApplication.pageQuery(searchModel);
//        Assert.assertNotNull(goodsListVoPageData);
//    }
//}