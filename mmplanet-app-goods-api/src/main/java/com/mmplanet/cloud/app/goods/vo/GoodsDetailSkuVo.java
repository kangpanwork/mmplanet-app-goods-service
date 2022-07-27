package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsDetailSkuVo implements Serializable{
private static final long serialVersionUID=1L;
    
	/**
	* 主键
	**/
	private String id;

	/**
	* 规格组合
	**/
	private String specDesc;



	/**
	* 标准售价
	**/
	private BigDecimal price;

	/**
	* 库存
	**/
	private Integer stock;

	/**
	* 图片
	**/
	private String images;


	/**
	 * 限购数量
	 */
	private Integer restrictionNum;
}
