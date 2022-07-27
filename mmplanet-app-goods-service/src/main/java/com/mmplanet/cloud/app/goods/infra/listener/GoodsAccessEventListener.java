package com.mmplanet.cloud.app.goods.infra.listener;

import com.mmplanet.cloud.app.goods.domain.event.GoodsAccessEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/29 14:49 <br>
 * @Author: niujiao
 */
@Component
public class GoodsAccessEventListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @EventListener
    @Async
    public void consume(GoodsAccessEvent event) {
        GoodsAccessEvent.Item item = event.getItem();
        String userId = item.getUserId();
        String shopId = item.getShopId();
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(shopId)) {
            String dayKey = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
            String key = String.join(":", "stat", shopId, dayKey);
            redisTemplate.opsForSet().add(key, userId);
        }
    }
}
