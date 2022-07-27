package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.dto.GoodsManageAuditPageQueryDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品审核表 服务类
 * </p>
 *
 * @author niujiao
 * @since 2022-05-26
 */
public interface GoodsAuditService extends IService<GoodsAuditEntity> {

    /**
     * 商品申请列表
     *
     * @param requestBean
     * @return
     */
    PageData<GoodsManageAuditListVo> page(SearchModel<GoodsManageAuditPageQueryDto> requestBean);

    /**
     * 删除商品申请
     *
     * @param auditIds
     * @return
     */
    Boolean removeByIds(List<String> auditIds, String opUserId);

    /**
     * OPS审核列表
     * @param pageParam
     * @return
     */
    Page<Map<String, Object>> auditPage(Page<?> pageParam, Map<String, Object> queryParam);
}
