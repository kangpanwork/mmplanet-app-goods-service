package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AttributeVo  implements Serializable{
private static final long serialVersionUID=1L;

	/**
	* 属性名称
	**/
	private String attrName;
	/**
	* 排序
	**/
	private Integer sort;

	/**
	* 备选值
	**/
	private List<String> attrValues;

}
