package com.mmplanet.cloud.app.goods.infra.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 延迟任务表
 * </p>
 *
 * @author niujiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_delayed_task")
public class DelayedTaskEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务标识
     */
    private String businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 触发操作
     */
    private String triggerOperation;

    /**
     * 触发时间
     */
    private Date triggerTime;

    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 状态: WAIT,SUCCESS,FAIL
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;


}
