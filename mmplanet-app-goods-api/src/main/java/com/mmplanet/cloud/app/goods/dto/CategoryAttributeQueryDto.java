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
 * @CreateDate: Created in 2022/5/20 18:00 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryAttributeQueryDto implements Serializable {

    /**
     * 类目ID
     */
    @NotBlank
    private String categoryId;
}
