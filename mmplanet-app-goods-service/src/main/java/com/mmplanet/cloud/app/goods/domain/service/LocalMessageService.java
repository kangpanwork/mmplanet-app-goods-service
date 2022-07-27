package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 本地消息表 服务类
 * </p>
 *
 * @author niujiao
 * @since 2022-07-11
 */
public interface LocalMessageService extends IService<LocalMessageEntity> {

    @Transactional(rollbackFor = Exception.class)
    boolean save(LocalMessageEntity entity);
}
