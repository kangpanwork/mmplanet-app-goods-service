package com.mmplanet.cloud.app.goods.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/21 18:39 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageDetailSkuVo implements Serializable {

    /**
     * SKU id
     */
    private String id;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 规格组合
     */
    private String specDesc;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 标准售价
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品货号
     */
    private String goodsCode;

    /**
     * 商家条形码
     */
    private String barcode;

    /**
     * 图片
     */
    private String images;


    /**
     * 限购数量
     */
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
     * 是否允许购买
     * 1=可购买，0=不可购买
     */
    private Integer isPurchase;
}
