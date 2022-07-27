package com.mmplanet.cloud.app.goods.api;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品表 接口提供
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@FeignClient(path = "/app/goods/goods", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface GoodsApi {

    /**
     * 商品搜索
     */
    @PostMapping("/V1.0/page")
    Response<PageData<GoodsListVo>> page(@Valid @RequestBody Request<SearchModel<GoodsPageQueryDto>> requestBean);

    /**
     * 批量查询
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/get")
    Response<List<GoodsListVo>> get(@Valid @RequestBody Request<BaseIdsDto> requestBean);

    /**
     * 创建补邮商品
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/createPostageGoods")
    Response<String> createPostageGoods(@Valid @RequestBody Request<CreatePostageGoodsDto> requestBean);

    @PostMapping("/simpleDetail")
    Response<SimpleGoodsVo> simpleDetail(@Valid @RequestBody Request<BaseIdDto> request);

    /**
     * 店铺首页商品列表分页查询
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/V1.0/shopHomePage")
    Response<PageData<GoodsListVo>> shopHomePage(@Valid @RequestBody Request<SearchModel<ShopHomePageQueryDto>> requestBean);

    /**
     * 商品管理：商品审核列表分页查询
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/auditPage")
    Response<PageData<GoodsManageAuditListVo>> manageAuditPage(@Valid @RequestBody Request<SearchModel<GoodsManageAuditPageQueryDto>> requestBean);

    /**
     * 商品管理：商品列表分页
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/page")
    Response<PageData<GoodsManageListVo>> managePage(@Valid @RequestBody Request<SearchModel<GoodsManagePageQueryDto>> requestBean);

    /**
     * 商品管理：上/下架
     */
    @PostMapping("manage/V1.0/UpAndDown")
    Response<Boolean> manageUpAndDown(@Valid @RequestBody Request<GoodsUpAndDownDto> requestBean);

    /**
     * 商品管理：审核通过统计
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/statistics")
    Response<GoodsManageStatisticsVo> manageStatistics(@Valid @RequestBody Request<GoodsManageStatisticsQueryDto> requestBean);


    /**
     * 商品管理：商品详情
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/detail")
    Response<GoodsManageDetailVo> manageDetail(@Valid @RequestBody Request<GoodsManageDetailDto> requestBean);

    /**
     * 商品管理：商品审核详情
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/AuditDetail")
    Response<GoodsManageAuditDetailVo> manageAuditDetail(@Valid @RequestBody Request<GoodsManageAuditDetailQueryDto> requestBean);

    /**
     * 商品详情
     */
    @PostMapping("/V1.0/detail")
    Response<GoodsDetailVo> detail(@Valid @RequestBody Request<BaseIdDto> requestBean);

    /**
     * 商品详情
     */
    @PostMapping("/V1.1/detail")
    Response<GoodsDetailVo> detailV1(@Valid @RequestBody Request<GoodsDetailQueryDto> requestBean);

    /**
     * 商品管理：新增或修改商品提交
     */
    @PostMapping("manage/V1.0/submitSave")
    Response<String> manageSubmitSave(@Valid @RequestBody Request<GoodsDto> requestBean);

    /**
     * 商品管理：删除
     */
    @PostMapping("manage/V1.0/del")
    Response<Boolean> manageDelete(@Valid @RequestBody Request<GoodsManageDeleteDto> requestBean);

    /**
     * 商品管理：审核删除
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/auditDel")
    Response<Boolean> manageAuditDelete(@Valid @RequestBody Request<GoodsManageAuditDeleteDto> requestBean);

    /**
     * 商品管理：商品置顶
     *
     * @param requestBean
     * @return
     */
    @PostMapping("manage/V1.0/top")
    Response<Boolean> manageTop(@Valid @RequestBody Request<GoodsManageTopDto> requestBean);


    @PostMapping("bindUser")
    Response<Boolean> bindUser(@Valid @RequestBody Request<GoodsBindUserDto> requestBean);


    @PostMapping("endActivity")
    Response<Boolean> endActivity(@Valid @RequestBody Request<EndActivityDto> requestBean);
}
