package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * GoodsManageAuditDeleteDto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageAuditDeleteDto implements Serializable{
private static final long serialVersionUID=1L;

	private String opUserId;

	/**
	 * 审核ID
	 */
	@NotNull
	@Size(min = 1)
	private List<String> auditIds;
}
