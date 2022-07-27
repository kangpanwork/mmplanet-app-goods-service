package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品sku信息表
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/17 12:55 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_sku")
public class SkuEntity implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 规格组合
     */
    @TableField("spec_desc")
    private String specDesc;

    /**
     * 重量
     */
    @TableField("weight")
    private BigDecimal weight;

    /**
     * 标准售价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 原价
     */
    @TableField("original_price")
    private BigDecimal originalPrice;

    /**
     * 成本价
     */
    @TableField("cost_price")
    private BigDecimal costPrice;

    /**
     * 库存
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 商品货号
     */
    @TableField("goods_code")
    private String goodsCode;

    /**
     * 商家条形码
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 图片
     */
    @TableField("images")
    private String images;

    /**
     * 限购数量
     */
    @TableField("restriction_num")
    private Integer restrictionNum;

    /**
     * 打样单号
     */
    @TableField("saas_no")
    private String saasNo;

    /**
     * 大货单号
     */
    @TableField("d_saas_no")
    private String dSaasNo;

    /**
     * 供应商ID
     */
    @TableField("dealer_id")
    private String dealerId;

    /**
     * 是否允许购买
     * 1=可购买，0=不可购买
     */
    @TableField("is_purchase")
    private Integer isPurchase;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;

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

}
