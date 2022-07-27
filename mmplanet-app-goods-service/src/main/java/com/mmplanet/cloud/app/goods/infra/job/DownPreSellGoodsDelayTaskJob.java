package com.mmplanet.cloud.app.goods.infra.job;

import com.mmplanet.cloud.app.goods.domain.service.DelayedTaskService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsService;
import com.mmplanet.cloud.app.goods.enums.DelayedTaskTriggerActionEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.infra.entity.DelayedTaskEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static com.mmplanet.cloud.app.goods.enums.DelayedTaskTypeEnum.GOODS;

/**
 * 下架预售商品
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/28 14:55 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class DownPreSellGoodsDelayTaskJob {

    @Autowired
    private DelayedTaskService delayedTaskService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisLockRegistry redisLockRegistry;


    @Scheduled(cron = "0 0/5 * * * ?")
    public void execute() {
        Lock lock = redisLockRegistry.obtain("APP:GOODS:DownPreSellGoodsDelayTaskJob");
        if (lock.tryLock()) {
            try {
                List<DelayedTaskEntity> delayedTaskEntities = delayedTaskService.queryWaitTask(
                        GOODS.name(), DelayedTaskTriggerActionEnum.GOODS_DOWN.name(),new Date());
                if (CollectionUtils.isEmpty(delayedTaskEntities)) {
                    return;
                }
                delayedTaskEntities.forEach(e -> {
                    try {
                        GoodsEntity goodsEntity = new GoodsEntity();
                        goodsEntity.setId(e.getBusinessId());
                        goodsEntity.setStatus(GoodsStatusEnum.DOWN.name());
                        goodsService.updateById(goodsEntity);
                        delayedTaskService.processResult(e.getId(), true);
                    } catch (Exception ex) {
                        log.error("", ex);
                        delayedTaskService.processResult(e.getId(), false);
                    }
                });
            } finally {
                lock.unlock();
            }
        }
    }
}
