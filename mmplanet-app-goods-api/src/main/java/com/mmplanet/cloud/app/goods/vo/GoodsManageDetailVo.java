package com.mmplanet.cloud.app.goods.vo;

import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/20 14:02 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageDetailVo implements Serializable {

    /**
     * 主键
     **/
    private String id;

    /**
     * 店铺id
     **/
    private String shopId;

    /**
     * 店铺类型
     */
    private String shopType;
    /**
     * 目录id
     **/
    private String categoryId;
    /**
     * 目录名称
     **/
    private String categoryName;

    /**
     * 类目ID路径
     */
    private String categoryIdPath;

    /**
     * 类目Name路径
     */
    private String categoryNamePath;

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
     * 商品缩略图
     **/
    private String smallImage;

    /**
     * 商品图片
     */
    List<String> images;

    /**
     * 销量
     **/
    private Integer saleNumber;

    /**
     * 商品详情
     */
    private String content;

    /**
     * 开始时间
     */
    private Date activityStartTime;

    /**
     * 结束时间
     */
    private Date activityEndTime;

    /**
     * 商品属性
     */
    private String attribute;

    /**
     * 属性描述
     */
    private String attributeDesc;


    /**
     * 运费类型
     *
     * @see com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum
     */
    private String expressType;

    /**
     *
     */
    private UnifiedExpressVo unifiedExpress;

    /**
     * 模板ID
     */
    private String expressTemplateId;


    /**
     * 运费模板名称
     */
    private String expressTemplateName;

    /**
     * 运费模板类型
     */
    private String expressTemplateType;

    /**
     * SKU列表
     */
    private List<GoodsManageDetailSkuVo> skuList;

    private List<GoodsDetailAttributeVo> attributeList;

    /**
     * 库存计数
     */
    private String stockReduce;

    /**
     * 总库存
     */
    private Integer totalStock;
    /**
     * 商品状态
     * @see GoodsStatusEnum
     */
    private String status;

    /**
     * 活动是否结束
     */
    private Boolean activityEnd;

    /**
     * 是否存在置顶
     *
     */
    private Integer isTop;

    /**
     * 店铺信息
     */
    private GoodsShopVo shop;
}
