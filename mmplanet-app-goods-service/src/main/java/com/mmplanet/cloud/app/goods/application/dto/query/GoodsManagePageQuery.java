package com.mmplanet.cloud.app.goods.application.dto.query;

import com.mmplanet.cloud.app.goods.dto.SortDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * GoodsPageQueryDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManagePageQuery implements Serializable{
private static final long serialVersionUID=1L;

	private String userId;

	/**
	 * 搜索词
	 */
	private String searchKeyword;

	/**
	 * 商品状态
	 * INIT("待上架"),
	 * UP("上架"),
	 * DOWN("下架")
	 */
	private String status;

	/**
	 * 排序
	 */
	private SortDto sort;

	/**
	 * 商品ID
	 */
	private String id;

	/**
	 * 商品名称（主标题）
	 */
	private String title;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 分类ID
	 */
	private String categoryId;

	/**
	 *
	 * shopId
	 */
	private String shopId;
}
