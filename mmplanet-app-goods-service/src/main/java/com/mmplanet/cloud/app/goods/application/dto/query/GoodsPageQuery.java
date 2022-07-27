package com.mmplanet.cloud.app.goods.application.dto.query;

import com.mmplanet.cloud.app.goods.dto.SortDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
public class GoodsPageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 排除商品ID
     */
    private List<String> excludeIds;

    /**
     * 排序  SALE("销量=综合"),PRICE("价格");
     */
    private SortDto sort;
}
