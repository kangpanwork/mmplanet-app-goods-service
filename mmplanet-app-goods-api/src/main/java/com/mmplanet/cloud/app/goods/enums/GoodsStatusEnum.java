package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/21 13:06 <br>
 * @Author: niujiao
 */
public enum GoodsStatusEnum {


    INIT("待上架"),
    UP("上架"),
    DOWN("下架"),
    DELETE("删除");

    private final String desc;

    GoodsStatusEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
