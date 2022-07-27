package com.mmplanet.cloud.app.goods.api.ops;


import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsDealerGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsListQueryDto;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditDetailVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageDetailVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageListVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 运营平台管理商品API
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 15:24 <br>
 * @Author: niujiao
 */

@FeignClient(path = "/ops/goods/goods", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface OpsGoodsApi {

    /**
     * 商品审核列表分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("/audit/page")
    Response<PageData<GoodsManageAuditListVo>> auditPage(@Valid @RequestBody Request<SearchModel<OpsGoodsAuditListQueryDto>> request);

    @PostMapping("/dealer/audit/page")
    Response<PageData<GoodsManageAuditListVo>> dealerAuditPage(@Valid @RequestBody Request<SearchModel<OpsDealerGoodsAuditListQueryDto>> request);


    /**
     * 商品列表
     *
     * @param request
     * @return
     */
    @PostMapping("/page")
    Response<PageData<GoodsManageListVo>> page(@Valid @RequestBody Request<SearchModel<OpsGoodsListQueryDto>> request);

    /**
     * 商品详情
     *
     * @return
     */
    @PostMapping("/detail")
    Response<GoodsManageDetailVo> detail(@Valid @RequestBody Request<BaseIdDto> request);

    /**
     * 商品审核详情
     *
     * @param request
     * @return
     */
    @PostMapping("/audit/detail")
    Response<GoodsManageAuditDetailVo> auditDetail(@Valid @RequestBody Request<BaseIdDto> request);


    /**
     * 供应商审核详情
     *
     * @param request
     * @return
     */
    @PostMapping("dealer/audit/detail")
    Response<GoodsManageAuditDetailVo> dealerAuditDetail(@Valid @RequestBody Request<GetDealerAuditDetailDto> request);

    /**
     * 商品审核
     *
     * @param request
     * @return
     */
    @PostMapping("/manageAudit")
    Response<Boolean> manageAudit(@Valid @RequestBody Request<GoodsAuditDto> request);

    /**
     * 商品删除
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/del")
    Response<Boolean> manageDelete(@Valid @RequestBody Request<GoodsManageDeleteDto> requestBean);


    /**
     * 商品提交（需要审核）
     *
     * @param request
     * @return
     */
    @PostMapping("/auditSave")
    Response<String> auditSave(@Valid @RequestBody Request<GoodsDto> request);


    @PostMapping("dealer/apply/page")
    Response<PageData<GoodsManageAuditListVo>> dealerApplyPage(@Valid @RequestBody Request<SearchModel<GoodsManageAuditPageQueryDto>> request);

    /**
     * 商品修改
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/modify")
    Response<Boolean> modify(Request<GoodsDto> requestBean);

    /**
     * 商品新增
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/add")
    Response<Boolean> add(Request<GoodsDto> requestBean);

    /**
     * 商品上下架
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/upAndDown")
    Response<Boolean> upAndDown(Request<GoodsUpAndDownDto> requestBean);
}
