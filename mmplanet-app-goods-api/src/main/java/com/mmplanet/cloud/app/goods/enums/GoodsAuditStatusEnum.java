package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/21 13:05 <br>
 * @Author: niujiao
 */
public enum GoodsAuditStatusEnum {

    TO_AUDIT("待审核"),
    AUDIT("审核中"),
    PASS("审核通过"),
    REJECT("驳回");

    GoodsAuditStatusEnum(String desc) {
        this.desc = desc;
    }

    private final String desc;

    public String getDesc() {
        return desc;
    }
}
