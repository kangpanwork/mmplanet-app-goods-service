package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class GoodsDetailVo implements Serializable {

    /**
     * 主键
     **/
    private String id;

    /**
     * 店铺所有者类型
     */
    private String shopType;

    /**
     * 店铺id
     **/
    private String shopId;

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
     * 商品主图
     */
    private List<String> images;
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


    /***
     * 距离活动开始
     */
    private Long distanceToActivitySt;

    /**
     * 是否可购买 true：是，false = 否
     */
    private Boolean purchasable;

    /**
     * 活动是否结束
     */
    private Boolean activityEnd;

    /**
     * 商品属性
     */
    private String attribute;

    /**
     * 属性描述
     */
    private String attributeDesc;

    /**
     * 店铺所有者ID
     */
    private String userId;

    /**
     *
     */
    private String status;

    /**
     * 运费类型
     *
     * @see com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum
     */
    private String expressType;

    /**
     * 运费类型值
     */
    @Deprecated
    private String expressTypeValue;

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
    private List<SkuVo> skuList;

    /**
     * 商品属性值
     */
    private List<GoodsDetailAttributeVo> attributeList;

    /**
     * 是否收藏
     */
    private boolean isFavorite;

    /**
     * 收藏ID
     */
    private String favoriteId;

    /**
     * 店铺信息
     */
    private GoodsShopVo shop;

    /**
     * 评论
     */
    private CommentVo comment;


    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class SkuVo{
        /**
         * 主键
         **/
        private String id;
        /**
         * 规格组合
         **/
        private String specDesc;

        /**
         * 图片
         **/
        private String images;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class CommentVo{
        private Long total;
        private List<CommentItemVo> items;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class CommentItemVo{

        /**
         * 图片
         */
        private String[] commentImageArray;

        /**
         * 买家昵称
         */
        private String userNickname;

        /**
         * 买家头像
         */
        private String userHeaderImage;

        /**
         * 评论内容
         */
        private String content;

        /**
         * 评论时间
         */
        private Date createTime;

        private String anonymous;
    }
}
