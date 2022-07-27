package com.mmplanet.cloud.app.goods.vo;

import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品管理列表Vo
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/20 14:05 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageListVo implements Serializable {

    /**
     * 主键
     **/
    private String id;

    /**
     * 用户类型
     * NORMAL("普通消费者"),
     * SMALL_B("小B商户"),
     * DEALER("经销商");
     */
    private String shopType;


    private String shopId;
    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 分类描述
     */
    private String categoryName;

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
     * @see GoodsStatusEnum
     */
    private String status;

    /**
     * 商品状态描述
     */
    private String statusDesc;
    /**
     * 是否置顶
     * @see TureOrFalseEnum
     */
    private Integer isTop;

    /**
     * 添加时间
     */
    private Date createTime;
}
