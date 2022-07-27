package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/23 14:19 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeleteAttributeDto implements Serializable {

    /**
     * 属性ID
     */
    private String id;

    private String opUserId;
}
