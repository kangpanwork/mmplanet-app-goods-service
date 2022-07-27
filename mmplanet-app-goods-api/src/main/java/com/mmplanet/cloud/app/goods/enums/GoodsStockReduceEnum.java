package com.mmplanet.cloud.app.goods.enums;

/**
 * 库存扣减枚举
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/25 13:32 <br>
 * @Author: niujiao
 */
public enum GoodsStockReduceEnum {

    ORDER_REDUCE("下单扣减库存"), PAY_REDUCE("付款减库存");
    private final String desc;

    GoodsStockReduceEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
