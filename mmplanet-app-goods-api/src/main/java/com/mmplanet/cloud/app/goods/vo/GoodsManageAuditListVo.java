package com.mmplanet.cloud.app.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品管理 审核列表返回VO
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 14:14 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageAuditListVo implements Serializable {

    /**
     * 审核ID
     */
    private String id;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 用户类型
     * NORMAL("普通消费者"),
     * SMALL_B("小B商户"),
     * DEALER("经销商");
     */
    private String shopType;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 商品主标题
     */
    private String title;

    /**
     * 商品副标题
     */
    private String subTitle;

    /**
     * 商品类型
     * @see GoodsTypeEnum
     */
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
     * 拒绝愿意
     */
    private String rejectReason;

    /**
     * 销量
     **/
    private Integer saleNumber;

    /**
     * 加购数
     */
    private Integer purchaseNumber;

    /**
     * 库存
     */
    private Integer totalStock;

    /**
     * 审核状态
     * @see GoodsAuditStatusEnum
     */
    private String auditStatus;

    /**
     * 商品状态
     */
    private String status;

    /**
     * 商品状态描述
     */
    private String statusDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核时间
     */
    private String auditUser;

    /**
     * 当前处理节点
     */
    private String handleNode;

    /**
     * 供应商Id
     */
    private String dealerId;

    @JsonIgnore
    private String detailContent;

    @JsonIgnore
    private String categoryId;

    @JsonIgnore
    private String shopId;
}
