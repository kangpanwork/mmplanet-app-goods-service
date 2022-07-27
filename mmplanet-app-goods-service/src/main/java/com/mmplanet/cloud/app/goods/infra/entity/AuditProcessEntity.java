package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 审核过程表
 * </p>
 *
 * @author niujiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_audit_process")
public class AuditProcessEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 流程ID
     */
    private String processId;

    /**
     * 阶段/步骤
     */
    private Integer step;

    /**
     * 处理节点
     */
    private String handleNode;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 审核ID（业务ID）
     */
    private String auditId;

    /**
     */
    private String auditStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;


}
