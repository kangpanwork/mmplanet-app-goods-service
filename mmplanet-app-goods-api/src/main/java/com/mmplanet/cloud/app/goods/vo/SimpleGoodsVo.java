package com.mmplanet.cloud.app.goods.vo;

import com.mmplanet.cloud.app.goods.constraints.ValueOfEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum;
import com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/24 13:17 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SimpleGoodsVo implements Serializable {

    /**
     * 商品ID
     */
    private String id;

    /**
     * 店铺所有者userID
     */
    private String userId;

    /**
     * SMALL_B("小B商户"),
     * DEALER("经销商")
     * 店铺类型
     */
    private String shopType;
    /**
     * 店铺id
     **/
    @NotBlank
    private String shopId;
    /**
     * 目录id
     **/
    @NotBlank
    private String categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目ID路径
     */
    private String categoryIdPath;

    /**
     * 类目名称路径
     */
    private String categoryNamePath;

    /**
     * 商品标题
     **/
    @NotBlank
    private String title;
    /**
     * 商品副标题
     **/
    private String subTitle;
    /**
     * 商品内容
     **/
    @NotBlank
    private String content;
    /**
     * 品牌
     **/
    private String brand;
    /**
     * 商品类型编码
     * @see GoodsTypeEnum
     **/
    @NotBlank
    @ValueOfEnum(enumClass = GoodsTypeEnum.class)
    private String goodsTypeCode;
    /**
     * 开始时间
     **/
    private Date activityStartTime;
    /**
     * 结束时间
     **/
    private Date activityEndTime;
    /**
     * 商品属性
     * @see TureOrFalseEnum
     **/
    private String attribute;
    /**
     * 属性描述
     **/
    private String attributeDesc;
    /**
     * 商品缩略图
     **/
    private String smallImage;
    /**
     * 商品主图
     **/
    @NotNull
    @Size(min = 1)
    private List<String> images;


    /**
     * SKU最小售价
     */
    private BigDecimal minSellingPrice;

    /**
     * SKU最大售价
     */
    private BigDecimal maxSellingPrice;

    /**
     * 库存计数
     * @see com.mmplanet.cloud.app.goods.enums.GoodsStockReduceEnum
     **/
    private String stockReduce;

    /**
     * 运费类型
     *  TEMPLATE("模板"),
     *  FREE("免费"),
     *  UNIFIED("统一")
     */
    @NotBlank
    @ValueOfEnum(enumClass = GoodsExpressTypeEnum.class)
    private String expressType;

    /**
     * 是否置顶
     * @see TureOrFalseEnum
     */
    private Integer isTop;

    /**
     * 运费类型值
     */
    private String expressTypeValue;
}
