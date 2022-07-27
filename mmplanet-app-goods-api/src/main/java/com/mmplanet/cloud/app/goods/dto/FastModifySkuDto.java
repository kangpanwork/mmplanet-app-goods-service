package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/15 18:22 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FastModifySkuDto implements Serializable {


    private String opUserId;
    /**
     * 商品ID
     */
    @NotBlank
    private String goodsId;

    /**
     * SKU信息
     */
    @NotNull
    @Size(min = 1)
    private List<SkuItemDto> skuList;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class SkuItemDto{
        /**
         * SKU ID
         */
        @NotBlank
        private String id;

        /**
         * 标准售价
         */
        @NotBlank
        private BigDecimal price;

        /**
         *
         */
        @NotBlank
        private Integer stock;
    }
}
