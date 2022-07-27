package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/17 12:55 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_goods")
public class GoodsEntity implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    @TableField("shop_type")
    private String shopType;
    /**
     * 店铺id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 目录id
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 目录名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 目录路径
     */
    @TableField("category_path")
    private String categoryPath;

    /**
     * 商品标题
     */
    @TableField("title")
    private String title;

    /**
     * 商品副标题
     */
    @TableField("sub_title")
    private String subTitle;

    /**
     * 商品内容
     */
    @TableField("content")
    private String content;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 商品类型编码
     */
    @TableField("goods_type_code")
    private String goodsTypeCode;

    /**
     * 类型名称
     */
    @TableField("goods_type_name")
    private String goodsTypeName;

    /**
     * 开始时间
     */
    @TableField("activity_start_time")
    private Date activityStartTime;

    /**
     * 结束时间
     */
    @TableField("activity_end_time")
    private Date activityEndTime;

    /**
     * 商品属性
     */
    @TableField("attribute")
    private String attribute;

    /**
     * 属性描述
     */
    @TableField("attribute_desc")
    private String attributeDesc;

    /**
     * 商品缩略图
     */
    @TableField("small_image")
    private String smallImage;

    /**
     * 商品主图
     */
    @TableField("images")
    private String images;

    /**
     * 库存计数
     */
    @TableField("stock_reduce")
    private String stockReduce;

    /**
     * 计价单位
     */
    @TableField("unit")
    private String unit;

    /**
     * 商品规格
     */
    @TableField("goods_spec")
    private String goodsSpec;

    @TableField("min_selling_price")
    private BigDecimal minSellingPrice;

    @TableField("max_selling_price")
    private BigDecimal maxSellingPrice;
    /**
     * 商品状态
     */
    @TableField("status")
    private String status;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 审核人
     */
    @TableField("audit_user")
    private String auditUser;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private Date auditTime;

    /**
     * 备注说明
     */
    @TableField("remark")
    private String remark;

    /**
     * 物流运费
     */
    @TableField("express_cost")
    private String expressCost;

    /**
     * 销量
     */
    @TableField("sale_number")
    private Integer saleNumber = 0;

    /**
     * 运费类型
     */
    @TableField("express_type")
    private String expressType;

    /**
     * 运费类型值
     */
    @TableField("express_type_value")
    private String expressTypeValue;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;

    /**
     * 总库存
     */
    @TableField("total_stock")
    private Integer totalStock;

    /**
     * 是否置顶
     */
    @TableField("is_top")
    private Integer isTop;

    private String buyerId;

    /**
     * 活动结束
     */
    private Integer activityEnd;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    private String updateUser;
}
