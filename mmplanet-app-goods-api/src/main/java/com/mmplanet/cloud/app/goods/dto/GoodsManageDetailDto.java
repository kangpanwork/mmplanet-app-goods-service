package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品管理状态统计
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/20 14:46 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageDetailDto implements Serializable {

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
