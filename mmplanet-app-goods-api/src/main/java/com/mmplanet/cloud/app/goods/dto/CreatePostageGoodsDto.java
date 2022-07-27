package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 18:20 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CreatePostageGoodsDto implements Serializable {

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 店铺所有者ID
     */
    private String userId;

    /**
     * 价格
     */
    private BigDecimal price;
}
