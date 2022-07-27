package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/23 11:35 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_goods_manage_statistic")
public class GoodsManageStatisticsEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 店铺id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 待审核数量
     */
    @TableField("to_audit_num")
    private Integer toAuditNum;

    /**
     * 通过数量
     */
    @TableField("pass_num")
    private Integer passNum;

    /**
     * 驳回数量
     */
    @TableField("reject_num")
    private Integer rejectNum;
}
