package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 13:34 <br>
 * @Author: niujiao
 */
public enum FreightTemplateTypeEnum {

    PREPAYMENT("预付模板"),
    POSTAGE("补邮模板");

    private final String desc;

    FreightTemplateTypeEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
