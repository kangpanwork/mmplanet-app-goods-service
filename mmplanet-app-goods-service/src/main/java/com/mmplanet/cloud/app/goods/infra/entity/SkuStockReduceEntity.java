package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 16:00 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_sku_stock_reduce")
public class SkuStockReduceEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    /** skuId */
    @TableId(value = "sku_id")
    private String skuId ;
    /** 订单ID */
    @TableId(value = "business_id")
    private String businessId ;
    @TableId(value = "business_type")
    private String businessType;
    /** 扣减类型：ADD、REDUCE */
    @TableId(value = "type")
    private String type ;
    /** 数量 */
    @TableId(value = "num")
    private Integer num ;
    /** 创建时间 */
    @TableId(value = "create_time")
    private Date createTime ;
    /** 创建人 */
    @TableId(value = "create_user")
    private String createUser ;
}
