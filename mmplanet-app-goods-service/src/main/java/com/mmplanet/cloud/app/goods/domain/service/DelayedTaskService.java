package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.goods.infra.entity.DelayedTaskEntity;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 延迟任务表 服务类
 * </p>
 *
 * @author niujiao
 * @since 2022-06-13
 */
public interface DelayedTaskService extends IService<DelayedTaskEntity> {

    /**
     * 新增延迟任务
     *
     * @param businessId
     * @param businessType
     * @param triggerOperation
     * @param triggerTime
     * @param extend
     */
    void save(String businessId, String businessType, String triggerOperation, Date triggerTime, String extend);

    /**
     * 获取执行任务
     * @param businessType
     * @param triggerOperation
     * @return
     */
    List<DelayedTaskEntity> queryWaitTask(String businessType,String triggerOperation,Date triggerTime);


    /**
     *
     * @param id 延迟任务ID
     * @param result true：成功，false：失败
     */
    void processResult(Long id, boolean result);
}
