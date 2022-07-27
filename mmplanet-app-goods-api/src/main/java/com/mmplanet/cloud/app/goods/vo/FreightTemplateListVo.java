package com.mmplanet.cloud.app.goods.vo;

import com.mmplanet.cloud.app.goods.enums.FreightTemplateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 13:32 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FreightTemplateListVo implements Serializable {

    private String id;
    /**
     * 模板名字
     */
    private String name;


    private String type;

    /**
     * 店铺ID
     */
    private String shopId;
}
