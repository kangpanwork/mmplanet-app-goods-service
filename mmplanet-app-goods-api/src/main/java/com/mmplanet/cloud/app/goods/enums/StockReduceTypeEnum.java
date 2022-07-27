package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 16:29 <br>
 * @Author: niujiao
 */
public enum StockReduceTypeEnum {

    ADD("增加"),
    REDUCE("减少");
    private final String desc;

    StockReduceTypeEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
