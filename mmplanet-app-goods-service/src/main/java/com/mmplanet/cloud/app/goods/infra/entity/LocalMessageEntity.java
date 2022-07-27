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
 * 本地消息表
 * </p>
 *
 * @author niujiao
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chh_local_message")
public class LocalMessageEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * msgId
     */
    private String msgId;

    /**
     * 操作对象
     */
    private String opObj;

    /**
     * 操作对象id
     */
    private String opObjId;

    /**
     * 操作事件
     */
    private String opEvent;

    /**
     * 触发动作
     */
    private String triggerAction;

    /**
     * 触发动作参数
     */
    private String triggerActionParam;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态:0=默认，1=成功，-1=失败
     */
    private Integer status;

    /**
     * 逻辑删除标识,1=表示已删除
     */
    private Integer deleted;


}
