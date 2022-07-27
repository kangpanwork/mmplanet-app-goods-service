package com.mmplanet.cloud.app.goods.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mmplanet.cloud.app.goods.constraints.PreSellGoodsDateValid;
import com.mmplanet.cloud.app.goods.constraints.ValueOfEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum;
import com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
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
@PreSellGoodsDateValid(message="预售结束日期不能小于预售开始日期")
public class GoodsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 审核ID
     */
    private String auditId;

    /**
     * 商品ID
     */
    private String id;

    /**
     * 店铺所有者userID
     */
    private String userId;

    /**
     * SMALL_B("小B商户"),
     * DEALER("经销商")
     * 店铺类型
     */
    private String shopType;
    /**
     * 店铺id
     **/
    private String shopId;
    /**
     * 目录id
     **/
    @NotBlank(message = "商品类目必填")
    private String categoryId;

    /**
     * 商品标题
     **/
    @NotBlank(message = "商品标题必填")
    private String title;
    /**
     * 商品副标题
     **/
    private String subTitle;
    /**
     * 商品内容
     **/
    private String content;
    /**
     * 品牌
     **/
    private String brand;
    /**
     * 商品类型编码
     *
     * @see GoodsTypeEnum
     **/
    @NotBlank(message = "商品类型必填")
    @ValueOfEnum(enumClass = GoodsTypeEnum.class)
    private String goodsTypeCode;
    /**
     * 开始时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date activityStartTime;
    /**
     * 结束时间
     **/
    @Future(message = "预售结束时间不能小于当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date activityEndTime;
    /**
     * 商品属性
     *
     * @see com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum
     **/
    @NotBlank(message="商品属性必填")
    private String attribute;
    /**
     * 属性描述
     **/
    private String attributeDesc;
    /**
     * 商品缩略图
     **/
    @NotBlank(message="商品缩略图必填")
    private String smallImage;
    /**
     * 商品主图
     **/
    @NotNull
    @Size(min = 1, message = "商品主图必填")
    private List<String> images;
    /**
     * 库存计数
     *
     * @see com.mmplanet.cloud.app.goods.enums.GoodsStockReduceEnum
     **/
    private String stockReduce;

    /**
     * 运费类型
     * TEMPLATE("模板"),
     * FREE("免费"),
     * UNIFIED("统一")
     */
    @NotBlank(message = "运费类型必填")
    @ValueOfEnum(enumClass = GoodsExpressTypeEnum.class)
    private String expressType;

    /**
     * 是否置顶
     *
     * @see TureOrFalseEnum
     */
    @NotNull(message="是否置顶必填")
    private Integer isTop;


    private String expressTypeValue;

    /**
     * 统一运费
     */
    private UnifiedExpressDto unifiedExpress;

    /**
     * 模板ID
     */
    private String expressTemplateId;

    /**
     * SKU列表
     */
    @NotNull(message = "SKU信息必填")
    @Size(min = 1, message = "SKU信息必填")
    @Valid
    private List<SkuDto> skuList;

    /**
     * 商品规格属性
     */
    @NotNull(message = "商品规格必填")
    @Size(min = 1, message = "商品规格必填")
    @Valid
    private List<GoodsDetailAttributeDto> attributeList;

    /**
     *操作人
     */
    private String opUserId;
}
