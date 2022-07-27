package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * GoodsDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UpdateGoodsDto implements Serializable{
private static final long serialVersionUID=1L;

	@JsonIgnore
	private String userId;

	private String id;
	/**
	* 店铺id
	**/
	private String shopId;
	/**
	* 目录id
	**/
	private String categoryId;

	/**
	* 商品标题
	**/
	private String title;
	/**
	* 商品副标题
	**/
	private String subTitle;
	/**
	* 商品内容
	**/
	private String content;
	/**
	* 品牌
	**/
	private String brand;
	/**
	* 商品类型编码
	**/
	private String goodsTypeCode;
	/**
	* 开始时间
	**/
	private Date activityStartTime;
	/**
	* 结束时间
	**/
	private Date activityEndTime;
	/**
	* 商品属性
	**/
	private String attribute;
	/**
	* 属性描述
	**/
	private String attributeDesc;
	/**
	* 商品缩略图
	**/
	private String smallImage;
	/**
	* 商品主图
	**/
	private String images;
	/**
	* 库存计数
	**/
	private String stockReduce;
	/**
	* 计价单位
	**/
	private String unit;
	/**
	* 商品规格
	**/
	private String goodsSpec;

	/**
	* 物流运费
	**/
	private String expressCost;

	private List<SkuDto> skuList;

	@Data
	@EqualsAndHashCode(callSuper = false)
	@Accessors(chain = true)
	public static class SkuDto{


		/**
		 * 规格组合
		 */
		private String specDesc;

		/**
		 * 重量
		 */
		private Integer weight;

		/**
		 * 标准售价
		 */
		private BigDecimal price;

		/**
		 * 原价
		 */
		private BigDecimal originalPrice;

		/**
		 * 成本价
		 */
		private BigDecimal costPrice;

		/**
		 * 库存
		 */
		private Integer stock;

		/**
		 * 商品货号
		 */
		private String goodsCode;

		/**
		 * 商家条形码
		 */
		private String barcode;

		/**
		 * 图片
		 */
		private String images;
	}
}
