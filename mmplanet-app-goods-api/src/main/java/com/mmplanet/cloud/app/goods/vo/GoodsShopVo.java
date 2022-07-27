package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/20 16:45 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsShopVo implements Serializable {

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