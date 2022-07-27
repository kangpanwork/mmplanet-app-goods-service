package com.mmplanet.cloud.app.goods.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 15:22 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CalFreightDto implements Serializable {

    /**
     * 省编码
     */
    @NotBlank
    private String provinceCode;

    /**
     * 市编码
     */
    @NotBlank
    private String cityCode;


    /**
     * 区编码
     */
    @NotBlank
    private String areaCode;

    /**
     * 购买数量
     */
    @NotNull
    private Integer num;
    /**
     * 商品id
     */
    @NotBlank
    private String goodsId;
}
