package com.mmplanet.cloud.app.goods.dto;

import com.mmplanet.cloud.app.goods.enums.FreightTemplateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 14:38 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FreightTemplateQueryDto implements Serializable {

    /**
     * 店铺ID
     */
    private String shopId;


    private String userId;
    /**
     * 计费类型
     * @see FreightTemplateTypeEnum
     */
    private String type;
}
