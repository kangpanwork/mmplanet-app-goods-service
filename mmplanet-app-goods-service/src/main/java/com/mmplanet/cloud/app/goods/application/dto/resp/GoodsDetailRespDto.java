package com.mmplanet.cloud.app.goods.application.dto.resp;

import com.mmplanet.cloud.app.goods.vo.UnifiedExpressVo;
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
 * @CreateDate: Created in 2022/7/23 13:28 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsDetailRespDto implements Serializable {

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
    private List<SkuRespDto> skuList;

    /**
     * 商品属性值
     */
    private List<GoodsAttributeRespDto> attributeList;

    /**
     * 是否收藏
     */
    private boolean isFavorite;

    /**
     * 收藏ID
     */
    private String favoriteId;

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
     * 类型名称
     **/
    private String goodsTypeName;

    /**
     * 库存计数
     */
    private String stockReduce;

    /**
     * 总库存
     */
    private Integer totalStock;

    /**
     * 是否存在置顶
     *
     */
    private Integer isTop;


    /**
     * 店铺信息
     */
    private ShopRespDto shopRespDto;

    /**
     * 评论数据
     */
    private CommentRespDto commentRespDto;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class AttributeRespDto implements Serializable {

        /**
         * 序号
         */
        private Integer sn;

        /**
         * 属性名
         */
        private String attrName;

        /**
         * 属性值
         */
        private List<String> attrValues;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class ShopRespDto implements Serializable {

        /**
         * 店铺类型
         **/
        private String shopType;

        /**
         * 店铺名称
         **/
        private String shopName;

        /**
         * 店铺icon
         **/
        private String shopIcon;

        /**
         * 店铺等级
         **/
        private Integer shopLevel;
        /**
         * 回头率
         **/
        private BigDecimal returnRate;
        /**
         * 粉丝数
         **/
        private Integer fans;

        private Date lastLoginTime;

        private String lastLoginTimeDesc;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class CommentRespDto {
        private Long total;
        private List<CommentItemRespDto> items;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class CommentItemRespDto {

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
