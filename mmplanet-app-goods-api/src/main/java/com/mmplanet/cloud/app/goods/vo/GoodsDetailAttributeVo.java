package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/27 10:37 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsDetailAttributeVo implements Serializable {

    /**
     * 序号
     */
    private Integer sn;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 属性值
     */
    private List<String> attrValues;
}
