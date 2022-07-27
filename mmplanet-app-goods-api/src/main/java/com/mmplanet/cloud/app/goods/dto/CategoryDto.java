package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * CategoryDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryDto  implements Serializable{
private static final long serialVersionUID=1L;

	private String id;
	/**
	* 父主键
	**/
	private String pid;
	/**
	* 目录名称
	**/
	@NotBlank
	private String name;
	/**
	* 分类排序
	**/
	@NotNull
	private Integer sort;

	/**
	 * 操作人，后端自动取值
	 */
	private String opUserId;
}
