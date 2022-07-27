package com.mmplanet.cloud.app.goods.application;

import com.mmplanet.cloud.app.goods.domain.service.DelayedTaskService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsService;
import com.mmplanet.cloud.app.goods.enums.DelayedTaskTriggerActionEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.infra.entity.DelayedTaskEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static com.mmplanet.cloud.app.goods.enums.DelayedTaskTypeEnum.GOODS;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/22 10:44 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class GoodsDelayedTaskApplication {

    @Autowired
    private DelayedTaskService delayedTaskService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Transactional(rollbackFor = Exception.class)
    public Boolean downDeadLinePreSellGoods() {
        Lock lock = redisLockRegistry.obtain("APP:GOODS:DOWN_DEADLINE_PRE_SELL_GOODS_TASK_JOB");
        if (lock.tryLock()) {
            try {
                List<DelayedTaskEntity> delayedTaskEntities = delayedTaskService.queryWaitTask(
                        GOODS.name(), DelayedTaskTriggerActionEnum.GOODS_DOWN.name(),new Date());
                if (CollectionUtils.isEmpty(delayedTaskEntities)) {
                    return true;
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
        return true;
    }
}
