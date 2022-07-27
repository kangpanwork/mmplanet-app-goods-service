package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/21 13:06 <br>
 * @Author: niujiao
 */
public enum GoodsExpressTypeEnum {
    TEMPLATE("模板"),
    FREE("免费"),
    UNIFIED("统一");

    GoodsExpressTypeEnum(String desc){
        this.desc = desc;
    }

    private final String desc;

    public String getDesc() {
        return desc;
    }
}
