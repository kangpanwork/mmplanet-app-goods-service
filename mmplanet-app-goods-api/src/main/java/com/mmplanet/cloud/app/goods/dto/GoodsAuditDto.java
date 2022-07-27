package com.mmplanet.cloud.app.goods.dto;

import com.mmplanet.cloud.app.goods.constraints.ValueOfEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/23 20:51 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsAuditDto implements Serializable {

    /**
     * 商品审核ID
     */
    @NotBlank(message = "审核ID必填")
    private String id;

    /**
     * 审核状态
     *
     * @see com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum
     */
    @NotBlank(message = "审核状态必填")
    @ValueOfEnum(enumClass = GoodsAuditStatusEnum.class, message = "审核状态参数错误")
    private String auditStatus;

    /**
     * 当前处理节点
     */
    @NotBlank(message = "当前处理节点必填")
    private String handleNode;

    /**
     * 审核人
     */
    @NotBlank(message = "审核人必填")
    private String auditUser;

    /**
     * 备注：审核失败原因
     */
    private String rejectReason;
}
