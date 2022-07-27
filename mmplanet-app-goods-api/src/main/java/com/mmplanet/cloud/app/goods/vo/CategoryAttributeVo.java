package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryAttributeVo  implements Serializable{
private static final long serialVersionUID=1L;
    
	/**
	* 主键
	**/
	private Integer id;
	/**
	* 目录id
	**/
	private String categoryId;
	/**
	* 属性id
	**/
	private String attributeId;

}
