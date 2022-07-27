package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import lombok.experimental.Accessors;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * SkuPageQueryDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SkuPageQueryDto  implements Serializable{
private static final long serialVersionUID=1L;
    
	/**
	* 主键
	**/
	private String id;
	/**
	* 商品id
	**/
	private String goodsId;
	/**
	* 规格组合
	**/
	private String specDesc;
	/**
	* 重量
	**/
	private Integer weight;
	/**
	* 标准售价
	**/
	private BigDecimal price;
	/**
	* 原价
	**/
	private BigDecimal originalPrice;
	/**
	* 成本价
	**/
	private BigDecimal costPrice;
	/**
	* 库存
	**/
	private Integer stock;
	/**
	* 商品货号
	**/
	private String goodsCode;
	/**
	* 商家条形码
	**/
	private String barcode;
	/**
	* 图片
	**/
	private String images;
	/**
	* 创建时间
	**/
	private Date createTime;
	/**
	* 创建人
	**/
	private String createUser;
}
