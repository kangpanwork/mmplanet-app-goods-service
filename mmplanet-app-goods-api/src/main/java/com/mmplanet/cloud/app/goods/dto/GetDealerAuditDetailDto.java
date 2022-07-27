package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/6 13:15 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetDealerAuditDetailDto implements Serializable {

    /**
     * 供应商ID
     */
    @NotBlank(message = "供应商ID必填")
    private String dealerId;

    /**
     * 审核ID
     */
    @NotBlank(message="审核ID必填")
    private String id;
}
