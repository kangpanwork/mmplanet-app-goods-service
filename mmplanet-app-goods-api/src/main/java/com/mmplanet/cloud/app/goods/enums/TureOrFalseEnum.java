package com.mmplanet.cloud.app.goods.enums;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/23 17:31 <br>
 * @Author: niujiao
 */
public enum TureOrFalseEnum {
    TRUE(1),
    FALSE(0);

    TureOrFalseEnum(int value){
        this.value = value;
    }
    private final Integer value;

    public Integer getValue() {
        return value;
    }
}
