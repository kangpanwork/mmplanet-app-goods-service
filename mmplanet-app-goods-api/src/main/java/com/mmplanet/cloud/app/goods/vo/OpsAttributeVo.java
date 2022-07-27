package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/30 18:04 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpsAttributeVo implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;


    @NotNull
    @Size(min = 1)
    private List<String> selectValues;
}
