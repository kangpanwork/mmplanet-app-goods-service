package com.mmplanet.cloud.app.goods.enums;

/**
 * 商品管理页排序枚举
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/23 13:52 <br>
 * @Author: niujiao
 */
public enum AppGoodsManagePageSortFiledEnum {


    CREATE_TIME("添加时间"),
    STOCK("库存"),
    SALE("销量");

    private final String desc;

    AppGoodsManagePageSortFiledEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
