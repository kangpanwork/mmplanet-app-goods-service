package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.goods.constraints.ValueOfEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/20 15:29 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsUpAndDownDto implements Serializable {

    private String opUserId;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID必填")
    @Size(min = 1, message = "商品ID必填")
    private List<String> ids;

    /**
     * UP("上架"),
     * DOWN("下架");
     */
    @NotBlank(message = "上架/下架必填")
    @Pattern(regexp = "UP|DOWN")
    private String upOrDown;
}
