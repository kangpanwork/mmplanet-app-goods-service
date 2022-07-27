package com.mmplanet.cloud.app.goods.vo;

import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核详情
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 17:13 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageAuditDetailVo implements Serializable {

    /**
     * 审核ID
     */
    private String id;

    /**
     * 申请内容
     */
    private GoodsVo content;

    /**
     * 创建时间
     */
    private Date createTime;

    private String status;
}
