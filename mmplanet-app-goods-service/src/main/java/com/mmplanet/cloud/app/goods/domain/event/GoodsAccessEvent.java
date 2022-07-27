package com.mmplanet.cloud.app.goods.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/29 14:41 <br>
 * @Author: niujiao
 */
@Getter
public class GoodsAccessEvent extends ApplicationEvent {

    private final Item item;

    public GoodsAccessEvent(Object source, Item item) {
        super(source);
        this.item = item;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Item {

        private String goodsId;

        private String shopId;

        private String userId;
    }
}
