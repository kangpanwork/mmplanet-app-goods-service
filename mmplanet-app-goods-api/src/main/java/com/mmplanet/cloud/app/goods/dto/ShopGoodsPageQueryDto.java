package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * GoodsPageQueryDto
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
public class ShopGoodsPageQueryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String userId;
    /**
     * 店铺id
     **/
    private String shopId;
    /**
     * 目录id
     **/
    private String categoryId;

    /**
     * 商品类型
     * @see com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum
     */
    private String goodsTypeCode;

    /**
     * 搜索词
     */
    private String searchKeyword;

    /**
     * 排序  SALE("销量=综合"),PRICE("价格");
     */
    private SortDto sort;
}
