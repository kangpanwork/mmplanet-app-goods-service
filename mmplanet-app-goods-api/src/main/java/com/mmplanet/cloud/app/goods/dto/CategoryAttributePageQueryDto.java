package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import lombok.experimental.Accessors;



/**
 * CategoryAttributePageQueryDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryAttributePageQueryDto  implements Serializable{
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
