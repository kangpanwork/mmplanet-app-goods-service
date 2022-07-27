package com.mmplanet.cloud.app.goods.domain.event;

import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/11 19:02 <br>
 * @Author: niujiao
 */
@Getter
public class LocalMessageCreatedEvent extends ApplicationEvent {

    private final LocalMessageEntity localMessageEntity;

    public LocalMessageCreatedEvent(Object source, LocalMessageEntity localMessageEntity) {
        super(source);
        this.localMessageEntity = localMessageEntity;
    }
}
