package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsListVo implements Serializable{

	private static final long serialVersionUID=1L;
    
	/**
	* 主键
	**/
	private String id;

	/**
	* 店铺id
	**/
	private String shopId;

	/**
	* 商品标题
	**/
	private String title;
	/**
	* 商品副标题
	**/
	private String subTitle;

	/**
	 * 状态
	 */
	private String status;
	/**
	* 商品类型编码
	 * PRE_SELL("预售"),
	 * SELL("现货"),
	 * CUSTOM_MADE("定制")
	**/
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
	* 销量
	**/
	private Integer saleNumber;

	/**
	 * 总库存
	 */
	private Integer totalStock;
}
