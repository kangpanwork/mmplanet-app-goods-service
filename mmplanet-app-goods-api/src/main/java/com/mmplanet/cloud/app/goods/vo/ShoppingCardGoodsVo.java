package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车列表上显示的商品信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShoppingCardGoodsVo implements Serializable{
private static final long serialVersionUID=1L;
    
	/**
	 * 购物车记录主键
	 **/
	private String id;

	/**
	 * 商品主键
	 **/
	private String goodsId;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 店铺id
	 **/
	private String shopId;

	/**
	* 商品标题
	**/
	private String title;

	/**
	* 商品类型编码 presale instock customized
	**/
	private String goodsTypeCode;
	/**
	* 类型名称 预售 现货 定制
	**/
	private String goodsTypeName;
	/**
	* 开始时间
	**/
	private Date activityStartTime;
	/**
	* 结束时间
	**/
	private Date activityEndTime;
	/**
	* 商品属性 has(有属性)、none(无属性)
	**/
	private String attribute;

	/**
	* 商品缩略图
	**/
	private String smallImage;

	/**
	* 商品状态 激活：activated 下架：invalid
	**/
	private String status;

	/**
	 * 物流运费
	 **/
	private String expressCost;

	/**
	 * 销量
	 **/
	private Integer saleNumber;

	/**
	 * 商品下选中的sku信息，包含购买数量
	 **/
	private ShoppingCardSkuVo skuVo;


}
