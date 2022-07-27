//package com.mmplanet.cloud.app.goods.application.dto;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.io.Serializable;
//import java.util.List;
//
///**
// * 商品规格属性值List
// * @Company: chaohuhu © Copyright 2022<br>
// * @Description: <br>
// * @Project: mmplanet <br>
// * @CreateDate: Created in 2022/5/27 10:40 <br>
// * @Author: niujiao
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//public class GoodsDetailAttributeDto implements Serializable {
//
//
//    private Integer sn;
//
//    /**
//     * 属性名
//     */
//    @NotEmpty(message="属性名必填")
//    private String attrName;
//
//    /**
//     * 属性值
//     */
//    @NotNull(message = "属性值必填")
//    @Size(min = 1)
//    private List<String> attrValues;
//}
