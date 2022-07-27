package com.mmplanet.cloud.app.goods.application.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * SkuDto
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
public class SkuRespDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     **/
    private String id;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 规格组合
     **/
    @NotBlank(message = "规格组合必填")
    private String specDesc;
    /**
     * 重量
     **/
    private BigDecimal weight;
    /**
     * 标准售价
     **/
    @NotNull(message = "标准售价必填")
    private BigDecimal price;
    /**
     * 原价
     **/
    private BigDecimal originalPrice;
    /**
     * 成本价
     **/
    @NotNull(message = "成本价必填")
    private BigDecimal costPrice;
    /**
     * 库存
     **/
    @NotNull(message = "库存必填")
    private Integer stock;
    /**
     * 商品货号
     **/
    private String goodsCode;
    /**
     * 商家条形码
     **/
    private String barcode;
    /**
     * 图片
     **/
    private String images;

    /**
     * 限购数量
     */
    @NotNull(message = "限购数量必填")
    private Integer restrictionNum;

    /**
     * 打样单号
     */
    private String saasNo;

    /**
     * 大货单号
     */
    @JsonProperty("dSaasNo")
    private String dSaasNo;

    /**
     * 供应商ID
     */
    private String dealerId;

    /**
     * 是否允许购买
     * 1=可购买，0=不可购买
     */
    @NotNull(message = "是否允许购买必填")
    private Integer isPurchase;

    @NotNull(message = "SKU排序必填")
    private Integer sn;
}
