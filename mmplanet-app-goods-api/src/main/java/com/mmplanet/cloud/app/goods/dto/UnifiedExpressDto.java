package com.mmplanet.cloud.app.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/12 11:23 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UnifiedExpressDto {

    /**
     * 首费
     */
    private String firstCharge;

    /**
     * 续费
     */
    private String renewCharge;
}
