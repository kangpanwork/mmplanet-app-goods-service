package com.mmplanet.cloud.app.goods.enums;

/**
 * 商品类型枚举
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/21 13:16 <br>
 * @Author: niujiao
 */
public enum GoodsTypeEnum {

    PRE_SELL("预售"),
    POSTAGE("补邮"),
    SELL("现货"),

    PRI("私发"),

    CUSTOM_MADE("定制");
    private final String desc;

    GoodsTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
