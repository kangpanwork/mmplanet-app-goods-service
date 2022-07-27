package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 14:00 <br>
 * @Author: niujiao
 */
public enum FreightChargeWayEnum {

    PIECE("按件"),
    WEIGHT("重量"),
    VOLUME("体积");

    private final String desc;

    FreightChargeWayEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
