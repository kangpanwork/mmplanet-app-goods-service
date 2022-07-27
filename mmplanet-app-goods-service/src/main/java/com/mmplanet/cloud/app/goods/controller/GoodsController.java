package com.mmplanet.cloud.app.goods.controller;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.GoodsApi;
import com.mmplanet.cloud.app.goods.application.GoodsApplication;
import com.mmplanet.cloud.app.goods.application.GoodsAuditApplication;
import com.mmplanet.cloud.app.goods.application.SkuApplication;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManagePageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsPageQuery;
import com.mmplanet.cloud.app.goods.controller.convert.GoodsConvert;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品表 管理
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Slf4j
@RestController
@RequestMapping("/app/goods/goods")
public class GoodsController implements GoodsApi {

    @Autowired
    private GoodsApplication goodsApplication;

    @Autowired
    private SkuApplication skuApplication;

    @Autowired
    private GoodsAuditApplication goodsAuditApplication;

    @Autowired
    private GoodsConvert goodsConvert;

    @PostMapping("/V1.0/page")
    @Override
    public Response<PageData<GoodsListVo>> page(Request<SearchModel<GoodsPageQueryDto>> requestBean) {
        GoodsPageQuery query = goodsConvert.convert(requestBean.getData().getModel());
        return ResultUtils.construct(goodsApplication.pageQuery(requestBean.getData().getPage(), query));
    }

    @PostMapping("/get")
    public Response<List<GoodsListVo>> get(@Valid @RequestBody Request<BaseIdsDto> request) {
        return ResultUtils.construct(goodsApplication.get(request.getData()));
    }

    @Override
    public Response<String> createPostageGoods(Request<CreatePostageGoodsDto> requestBean) {
        return ResultUtils.construct(goodsApplication.createPostageGoods(requestBean.getData()));
    }

    @Override
    public Response<SimpleGoodsVo> simpleDetail(Request<BaseIdDto> requestBean) {
        return ResultUtils.construct(goodsApplication.simpleDetail(requestBean.getData()));
    }

    @PostMapping("/V1.0/shopHomePage")
    @Override
    public Response<PageData<GoodsListVo>> shopHomePage(Request<SearchModel<ShopHomePageQueryDto>> requestBean) {
        GoodsPageQuery query = goodsConvert.convert(requestBean.getData().getModel());
        return ResultUtils.construct(goodsApplication.shopHomePage(requestBean.getData().getPage(), query));
    }

    @PostMapping("manage/V1.0/auditPage")
    @Override
    public Response<PageData<GoodsManageAuditListVo>> manageAuditPage(Request<SearchModel<GoodsManageAuditPageQueryDto>> requestBean) {
        return ResultUtils.construct(goodsAuditApplication.manageAuditPage(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/page")
    @Override
    public Response<PageData<GoodsManageListVo>> managePage(Request<SearchModel<GoodsManagePageQueryDto>> requestBean) {
        GoodsManagePageQuery query = goodsConvert.convert(requestBean.getData().getModel());
        return ResultUtils.construct(goodsApplication.managePage(requestBean.getData().getPage(), query));
    }

    @PostMapping("manage/V1.0/UpAndDown")
    @Override
    public Response<Boolean> manageUpAndDown(Request<GoodsUpAndDownDto> requestBean) {
        return ResultUtils.construct(goodsApplication.manageUpAndDown(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/statistics")
    @Override
    public Response<GoodsManageStatisticsVo> manageStatistics(Request<GoodsManageStatisticsQueryDto> requestBean) {
        return ResultUtils.construct(goodsApplication.manageStatistics(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/detail")
    @Override
    public Response<GoodsManageDetailVo> manageDetail(Request<GoodsManageDetailDto> requestBean) {
        GoodsManageQuery query = new GoodsManageQuery();
        BeanUtils.copyProperties(requestBean.getData(), query);
        return ResultUtils.construct(goodsApplication.manageDetail(query));
    }

    @PostMapping("manage/V1.0/AuditDetail")
    @Override
    public Response<GoodsManageAuditDetailVo> manageAuditDetail(Request<GoodsManageAuditDetailQueryDto> requestBean) {
        return ResultUtils.construct(goodsAuditApplication.manageAuditDetail(requestBean.getData()));
    }

    @PostMapping("/V1.0/detail")
    @Override
    public Response<GoodsDetailVo> detail(Request<BaseIdDto> requestBean) {
        return ResultUtils.construct(goodsApplication.detail(requestBean.getData()));
    }

    @Override
    public Response<GoodsDetailVo> detailV1(Request<GoodsDetailQueryDto> requestBean) {
        return ResultUtils.construct(goodsApplication.detailV1(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/submitSave")
    @Override
    public Response<String> manageSubmitSave(Request<GoodsDto> requestBean) {
        return ResultUtils.construct(goodsAuditApplication.save(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/del")
    @Override
    public Response<Boolean> manageDelete(Request<GoodsManageDeleteDto> requestBean) {
        return ResultUtils.construct(goodsApplication.manageDelete(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/auditDel")
    @Override
    public Response<Boolean> manageAuditDelete(Request<GoodsManageAuditDeleteDto> requestBean) {
        return ResultUtils.construct(goodsAuditApplication.manageAuditDelete(requestBean.getData()));
    }

    @PostMapping("manage/V1.0/top")
    @Override
    public Response<Boolean> manageTop(Request<GoodsManageTopDto> requestBean) {
        return ResultUtils.construct(goodsApplication.manageTop(requestBean.getData()));
    }

    @PostMapping("bindUser")
    public Response<Boolean> bindUser(@Valid @RequestBody Request<GoodsBindUserDto> requestBean) {
        return ResultUtils.construct(goodsApplication.bindUser(requestBean.getData()));
    }

    @PostMapping("endActivity")
    public Response<Boolean> endActivity(@Valid @RequestBody Request<EndActivityDto> requestBean) {
        return ResultUtils.construct(goodsApplication.endActivity(requestBean.getData()));
    }
}
