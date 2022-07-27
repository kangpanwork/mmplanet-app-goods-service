package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/20 14:49 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageStatisticsVo implements Serializable {

    /**
     * 待审核数量
     */
    private Integer toAuditNum;

    /**
     * 通过数量
     */
    private Integer passNum;

    /**
     * 驳回数量
     */
    private Integer rejectNum;
}
