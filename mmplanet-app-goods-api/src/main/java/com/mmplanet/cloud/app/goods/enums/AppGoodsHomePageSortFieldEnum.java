package com.mmplanet.cloud.app.goods.enums;

/**
 * 商品首页排序枚举
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/23 13:48 <br>
 * @Author: niujiao
 */
public enum AppGoodsHomePageSortFieldEnum {

    SALE("销量"),
    TIME("上新"),
    PRICE("价格");

    private final String desc;

    AppGoodsHomePageSortFieldEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
