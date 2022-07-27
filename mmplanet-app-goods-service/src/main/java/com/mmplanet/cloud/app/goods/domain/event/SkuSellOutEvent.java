package com.mmplanet.cloud.app.goods.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/24 18:14 <br>
 * @Author: niujiao
 */
@Getter
public class SkuSellOutEvent extends ApplicationEvent {

    private final String skuId;

    public SkuSellOutEvent(Object source, String skuId) {
        super(source);
        this.skuId = skuId;
    }
}
