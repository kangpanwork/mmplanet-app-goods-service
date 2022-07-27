package com.mmplanet.cloud.app.goods.application.dto.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/23 14:43 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageQuery {

    private String userId;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 商品ID
     */
    private String id;
}
