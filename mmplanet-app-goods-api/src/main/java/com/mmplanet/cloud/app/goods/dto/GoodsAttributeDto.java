package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import lombok.experimental.Accessors;



/**
 * GoodsAttributeDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsAttributeDto  implements Serializable{
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
	* 属性名称
	**/
	private String name;
}
