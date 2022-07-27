
package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.service.DelayedTaskService;
import com.mmplanet.cloud.app.goods.enums.DelayedTaskStatusEnum;
import com.mmplanet.cloud.app.goods.infra.entity.DelayedTaskEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.DelayedTaskMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 延迟任务表 服务实现类
 * </p>
 *
 * @author niujiao
 * @since 2022-06-13
 */
@Service
public class DelayedTaskServiceImpl extends ServiceImpl<DelayedTaskMapper, DelayedTaskEntity> implements DelayedTaskService {


    @Override
    public void save(String businessId, String businessType, String triggerOperation, Date triggerTime, String extend) {
        // 覆盖相同任务
        LambdaQueryWrapper<DelayedTaskEntity> queryWrapper = Wrappers.<DelayedTaskEntity>lambdaQuery()
                .eq(DelayedTaskEntity::getBusinessId, businessId)
                .eq(DelayedTaskEntity::getBusinessType, businessType)
                .eq(DelayedTaskEntity::getTriggerOperation, triggerOperation)
                .in(DelayedTaskEntity::getStatus, DelayedTaskStatusEnum.WAIT.name());

        DelayedTaskEntity taskEntity = getOne(queryWrapper);
        if (taskEntity != null) {
            if (taskEntity.getTriggerTime().compareTo(triggerTime) == 0) {
                return;
            }
            super.removeById(taskEntity.getId());
        }

        DelayedTaskEntity entity = new DelayedTaskEntity();
        entity.setBusinessId(businessId);
        entity.setBusinessType(businessType);
        entity.setTriggerOperation(triggerOperation);
        entity.setTriggerTime(triggerTime);
        entity.setExtend(extend);
        entity.setStatus(DelayedTaskStatusEnum.WAIT.name());
        entity.setCreateTime(new Date());
        entity.setCreateUser("system");
        save(entity);
    }

    @Override
    public List<DelayedTaskEntity> queryWaitTask(String businessType, String triggerOperation,Date triggerTime) {
        // 覆盖相同任务
        LambdaQueryWrapper<DelayedTaskEntity> queryWrapper = Wrappers.<DelayedTaskEntity>lambdaQuery()
                .eq(DelayedTaskEntity::getBusinessType, businessType)
                .eq(DelayedTaskEntity::getTriggerOperation, triggerOperation)
                .in(DelayedTaskEntity::getStatus, DelayedTaskStatusEnum.WAIT.name())
                .le(DelayedTaskEntity::getTriggerTime,triggerTime)
                .orderByDesc(DelayedTaskEntity::getTriggerTime)
                .last("LIMIT 1000");

        return super.list(queryWrapper);
    }

    @Override
    public void processResult(Long id, boolean result) {
        DelayedTaskEntity entity = new DelayedTaskEntity();
        entity.setId(id);
        entity.setStatus(result ? DelayedTaskStatusEnum.SUCCESS.name() : DelayedTaskStatusEnum.FAIL.name());
        entity.setUpdateTime(new Date());
        updateById(entity);
    }
}
