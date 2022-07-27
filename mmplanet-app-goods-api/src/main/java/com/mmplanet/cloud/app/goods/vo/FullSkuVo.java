package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/25 17:55 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FullSkuVo implements Serializable {

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * sku id
     */
    private String id;

    /**
     * 商品所有者
     */
    private String userId;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品副标题
     */
    private String subTitle;

    /**
     * 商品类型编码
     * @see com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum
     */
    private String goodsTypeCode;

    /**
     * 类型名称
     */
    private String goodsTypeName;

    /**
     * 商品缩略图
     */
    private String goodsSmallImage;

    /**
     * 预售开始时间
     */
    private Date activityStartTime;

    /**
     * 预售结束时间
     */
    private Date activityEndTime;

    /**
     * 活动是否结束
     */
    private Boolean activityEnd;

    /**
     * 是否可购买 true：是，false = 否
     */
    private Boolean purchasable;

    /**
     * 商品属性
     */
    private String attribute;

    /**
     * 属性描述
     */
    private String attributeDesc;

    /**
     * 库存策略
     * @see com.mmplanet.cloud.app.goods.enums.GoodsStockReduceEnum
     */
    private String stockReduce;

    /**
     * 计价单位
     */
    private String unit;

    /**
     * SKU状态
     */
    private String status;

    /**
     * 商品状态
     * com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum
     */
    private String goodsStatus;

    /**
     * 运费类型
     * @see com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum
     */
    private String expressType;

    /**
     * 运费类型值
     */
    private String expressTypeValue;

    /**
     * SKU 规格组合
     */
    private String specDesc;

    /**
     * 重量
     */
    private Integer weight;

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
     * SKU 图片
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
    private String dSaasNo;
    /**
     * 供应商ID
     */
    private String dealerId;
}
