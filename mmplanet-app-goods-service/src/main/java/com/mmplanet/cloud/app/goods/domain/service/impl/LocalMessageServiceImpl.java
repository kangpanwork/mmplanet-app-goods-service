package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.common.util.UUIDHelper;
import com.mmplanet.cloud.app.goods.domain.event.GoodsAuditPassedEvent;
import com.mmplanet.cloud.app.goods.domain.event.LocalMessageCreatedEvent;
import com.mmplanet.cloud.app.goods.domain.service.LocalMessageService;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.LocalMessageMapper;
import com.mmplanet.cloud.app.goods.infra.util.SpringApplicationContentUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 本地消息表 服务实现类
 * </p>
 *
 * @author niujiao
 * @since 2022-07-11
 */
@Service
public class LocalMessageServiceImpl extends ServiceImpl<LocalMessageMapper, LocalMessageEntity> implements LocalMessageService {

    @Override
    public boolean save(LocalMessageEntity entity) {
        entity.setMsgId(UUIDHelper.getUUID());
        SpringApplicationContentUtils.getContext().publishEvent(new LocalMessageCreatedEvent(this, entity));
        return super.save(entity);
    }
}
