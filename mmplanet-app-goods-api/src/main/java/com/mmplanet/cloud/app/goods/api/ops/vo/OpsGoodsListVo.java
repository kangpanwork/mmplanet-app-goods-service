package com.mmplanet.cloud.app.goods.api.ops.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 15:59 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpsGoodsListVo implements Serializable {

    /**
     * 主键
     **/
    private String id;

    /**
     * 商品标题
     **/
    private String title;
    /**
     * 商品副标题
     **/
    private String subTitle;

    /**
     * 商品类型编码
     **/
    private String goodsTypeCode;
    /**
     * 类型名称
     **/
    private String goodsTypeName;

    /**
     * SKU最小售价
     */
    private BigDecimal minSellingPrice;

    /**
     * SKU最大售价
     */
    private BigDecimal maxSellingPrice;

    /**
     * 商品缩略图
     **/
    private String smallImage;

    /**
     * 销量
     **/
    private Integer saleNumber;

    /**
     * 加购数
     */
    private Integer purchaseNumber;

    /**
     * 添加时间
     */
    private String createTime;
}
