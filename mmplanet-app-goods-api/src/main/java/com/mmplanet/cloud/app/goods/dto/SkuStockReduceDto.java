package com.mmplanet.cloud.app.goods.dto;

import com.mmplanet.cloud.app.goods.constraints.ValueOfEnum;
import com.mmplanet.cloud.app.goods.enums.StockReduceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 16:18 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SkuStockReduceDto implements Serializable {

    /**
     * SKU id
     */
    @NotBlank
    private String skuId;

    /**
     * 业务ID：如订单ID
     */
    @NotBlank
    private String businessId;
    /**
     * 业务类型Type
     *
     * @see com.mmplanet.cloud.app.goods.enums.StockReduceBusinessTypeEnum
     */
    @NotBlank
    private String businessType;

    /**
     * 扣减类型：ADD、REDUCE
     * @see com.mmplanet.cloud.app.goods.enums.StockReduceTypeEnum
     */
    @NotBlank
    @ValueOfEnum(enumClass = StockReduceTypeEnum.class)
    private String type;

    /**
     * 数量
     */
    @NotNull
    @Min(value = 1)
    private Integer num;
}
