package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品审核表
 * </p>
 *
 * @author niujiao
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_goods_audit")
public class GoodsAuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 店铺类型
     */
    private String shopType;

    /**
     * 商品主标题
     */
    private String title;

    /**
     * 商品子标题
     */
    private String subTitle;

    /**
     * 目录id
     */
    private String categoryId;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 商品类型编码
     */
    private String goodsTypeCode;

    /**
     * 缩略图
     */
    private String smallImage;
    /**
     * SKU最小售价
     */
    private BigDecimal minSellingPrice;

    /**
     * SKU最大售价
     */
    private BigDecimal maxSellingPrice;
    /**
     * 详细内容，json格式
     */
    private String detailContent;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;


    private String updateUser;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;
}
