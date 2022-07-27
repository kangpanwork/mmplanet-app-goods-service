package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * GoodsDto
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsManageTopDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opUserId;

    /**
     * 商品ID
     */
    @NotNull(message="商品ID必填")
    @Size(min = 1,message="商品ID必填")
    private List<String> ids;

    /**
     * 是否置顶
     *
     * @see com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum
     */
    @NotNull(message="是否置顶必填")
    private Integer top;
}
