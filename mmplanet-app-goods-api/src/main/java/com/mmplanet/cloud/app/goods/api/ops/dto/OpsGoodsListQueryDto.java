package com.mmplanet.cloud.app.goods.api.ops.dto;

import com.mmplanet.cloud.app.goods.dto.SortDto;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 15:55 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpsGoodsListQueryDto implements Serializable {

    private String userId;
    /**
     * 商品ID
     */
    private String id;

    /**
     * 商品名称（主标题）
     */
    private String title;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 商品状态
     *
     * @see GoodsStatusEnum
     */
    private String status;

    private SortDto sort;
}
