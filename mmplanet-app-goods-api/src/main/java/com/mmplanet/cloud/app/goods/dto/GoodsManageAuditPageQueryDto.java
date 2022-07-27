package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品管理：商品审核列表分页查询对象
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageAuditPageQueryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * 搜索词
     */
    private String searchKeyword;

    /**
     * 审核状态：
     * TO_AUDIT("待审核")
     * REJECT("驳回")
     * PASS("审核通过")
     */
    private String auditStatus;

    /**
     * 排序
     */
    private SortDto sort;

    /**
     * 审核ID
     */
    private String id;

    private String goodsId;

    /**
     * 商品名称（主标题）
     */
    private String title;

    /**
     * 店铺名称
     */
    private String shopId;

    /**
     * 分类ID
     */
    private String categoryId;
}
