package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品下详细属性
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/17 12:55 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_goods_attribute")
public class GoodsAttributeEntity implements Serializable {


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
     * 属性名称
     */
    @TableField("name")
    private String name;


    private Integer sn;
}
