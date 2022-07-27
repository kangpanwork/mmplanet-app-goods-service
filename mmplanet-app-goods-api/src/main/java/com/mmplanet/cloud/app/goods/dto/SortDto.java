package com.mmplanet.cloud.app.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 排序Dto
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/20 14:22 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SortDto implements Serializable {

    /**
     * 排序key
     * SALE(销量)、STOCK(库存)和CREATE_TIME(创建时间)
     */
    private String key;

    /**
     * 排序方式：DESC=降序，ASC=升序
     */
    private String order;
}
