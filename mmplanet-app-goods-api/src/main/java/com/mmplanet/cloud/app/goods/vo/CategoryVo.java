package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryVo  implements Serializable{
private static final long serialVersionUID=1L;
    
	/**
	* 主键
	**/
	private String id;
	/**
	* 父主键
	**/
	private String pid;
	/**
	* 目录名称
	**/
	private String name;

	private Date createTime;

	private Integer hasChildren;

	/**
	* 分类排序
	**/
	private Integer sort;
}
