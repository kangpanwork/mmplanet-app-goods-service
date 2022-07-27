package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车商品查询所有规格入参
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShoppingCardSkusDto implements Serializable{
private static final long serialVersionUID=1L;
    
	/**
	* 选中规格id
	**/
	@NotBlank(message = "默认选中规格不能为空！")
	private String skuId;

	/**
	* 商品id
	**/
	@NotBlank(message = "商品信息不能为空！")
	private String goodsId;


}
