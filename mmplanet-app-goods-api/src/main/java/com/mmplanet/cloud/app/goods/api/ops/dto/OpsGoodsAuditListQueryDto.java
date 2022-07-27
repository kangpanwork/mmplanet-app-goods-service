package com.mmplanet.cloud.app.goods.api.ops.dto;

import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 15:42 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpsGoodsAuditListQueryDto implements Serializable {

    /**
     * 审核ID
     */
    private String id;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品类型
     */
    private String goodsTypeCode;

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

    /**
     * 审核状态
     * @see GoodsAuditStatusEnum
     */
    private String auditStatus;
}
