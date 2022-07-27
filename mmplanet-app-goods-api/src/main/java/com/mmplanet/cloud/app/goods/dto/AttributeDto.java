package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * AttributeDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AttributeDto  implements Serializable{
private static final long serialVersionUID=1L;

	private String id;
	/**
	* 属性名称
	**/
	@NotBlank(message="属性名称必填")
	private String name;
	/**
	* 排序
	**/
	@NotNull(message="排序必填")
	private Integer sort;

	/**
	* 备选值
	**/
	@NotNull(message = "属性值必填")
	@Size(min = 1,message="属性值必填")
	private List<String> selectValues;

	/**
	 * 操作人，后端自动取值
	 */
	private String opUserId;
}
