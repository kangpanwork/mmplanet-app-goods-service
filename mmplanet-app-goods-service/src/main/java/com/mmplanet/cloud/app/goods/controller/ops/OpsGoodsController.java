package com.mmplanet.cloud.app.goods.controller.ops;

import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.ops.OpsGoodsApi;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsDealerGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsListQueryDto;
import com.mmplanet.cloud.app.goods.application.GoodsApplication;
import com.mmplanet.cloud.app.goods.application.GoodsAuditApplication;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManagePageQuery;
import com.mmplanet.cloud.app.goods.controller.convert.GoodsConvert;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditDetailVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageDetailVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageListVo;
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
 * @CreateDate: Created in 2022/5/28 17:54 <br>
 * @Author: niujiao
 */
@Slf4j
@RestController
@RequestMapping("/ops/goods/goods")
public class OpsGoodsController implements OpsGoodsApi {

    @Autowired
    private GoodsApplication goodsApplication;

    @Autowired
    private GoodsAuditApplication goodsAuditApplication;

    @Autowired
    private GoodsConvert goodsConvert;

    /**
     * ops商品审核列表分页查询
     * @param request
     * @return
     */
    @PostMapping("/audit/page")
    public Response<PageData<GoodsManageAuditListVo>> auditPage(@Valid @RequestBody Request<SearchModel<OpsGoodsAuditListQueryDto>> request){
        return ResultUtils.construct(goodsAuditApplication.opsAuditPage(request.getData()));
    }

    /**
     * 经销商商品审核列表分页查询
     * @param request
     * @return
     */
    @Override
    public Response<PageData<GoodsManageAuditListVo>> dealerAuditPage(Request<SearchModel<OpsDealerGoodsAuditListQueryDto>> request) {
        return ResultUtils.construct(goodsAuditApplication.dealerAuditPage(request.getData()));
    }

    /**
     * 商品列表
     *
     * @param request
     * @return
     */
    @PostMapping("/page")
    public Response<PageData<GoodsManageListVo>> page(@Valid @RequestBody Request<SearchModel<OpsGoodsListQueryDto>> request){
        GoodsManagePageQuery query = goodsConvert.convert(request.getData().getModel());
        return ResultUtils.construct(goodsApplication.opsManagePage(request.getData().getPage(), query));
    }

    /**
     * 商品详情
     *
     * @return
     */
    @PostMapping("/detail")
    public Response<GoodsManageDetailVo> detail(@Valid @RequestBody Request<BaseIdDto> request){
        return ResultUtils.construct(goodsApplication.opsDetail(request.getData()));
    }

    /**
     * 商品审核详情
     *
     * @param request
     * @return
     */
    @PostMapping("/audit/detail")
    public Response<GoodsManageAuditDetailVo> auditDetail(@Valid @RequestBody Request<BaseIdDto> request){
        return ResultUtils.construct(goodsAuditApplication.opsAuditDetail(request.getData()));
    }



    /**
     * 经销商商品审核详情
     * @param request
     * @return
     */
    @PostMapping("dealer/audit/detail")
    public Response<GoodsManageAuditDetailVo> dealerAuditDetail(@Valid @RequestBody Request<GetDealerAuditDetailDto> request){
        return ResultUtils.construct(goodsAuditApplication.dealerAuditDetail(request.getData()));
    }

    /**
     * 经销商商品申请分页列表
     * @param request
     * @return
     */
    @PostMapping("dealer/apply/page")
    public Response<PageData<GoodsManageAuditListVo>> dealerApplyPage(@Valid @RequestBody Request<SearchModel<GoodsManageAuditPageQueryDto>> request){
        return ResultUtils.construct(goodsAuditApplication.manageAuditPage(request.getData()));
    }

    /**
     * 商品审核
     * @param request
     * @return
     */
    @PostMapping("manageAudit")
    public Response<Boolean> manageAudit(@Valid @RequestBody Request<GoodsAuditDto> request){
        return ResultUtils.construct(goodsAuditApplication.opsAudit(request.getData()));
    }

    /**
     * 添加商品（需要审核）
     * @param request
     * @return
     */
    @PostMapping("auditSave")
    public Response<String> auditSave(@Valid @RequestBody Request<GoodsDto> request){
        return ResultUtils.construct(goodsAuditApplication.save(request.getData()));
    }

    /**
     * 商品删除
     * @return
     */
    @PostMapping("/del")
    @Override
    public Response<Boolean> manageDelete(@Valid @RequestBody Request<GoodsManageDeleteDto> request) {
        return ResultUtils.construct(goodsApplication.opsDelete(request.getData()));
    }

    /**
     * 商品修改
     * @param request
     * @return
     */
    @PostMapping("/modify")
    @Override
    public Response<Boolean> modify(@Valid @RequestBody Request<GoodsDto> request) {
        return ResultUtils.construct(goodsApplication.opsModify(request.getData()));
    }

    /**
     * 商品新增
     * @param request
     * @return
     */
    @PostMapping("/add")
    @Override
    public Response<Boolean> add(@Valid @RequestBody Request<GoodsDto> request) {
        return ResultUtils.construct(goodsApplication.opsAdd(request.getData()));
    }

    /**
     * 商品上下架
     * @param requestBean
     * @return
     */
    @PostMapping("/upAndDown")
    @Override
    public Response<Boolean> upAndDown(@Valid @RequestBody Request<GoodsUpAndDownDto> requestBean) {
        return ResultUtils.construct(goodsApplication.opsUpAndDown(requestBean.getData()));
    }
}
