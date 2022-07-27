package com.mmplanet.cloud.app.goods.domain.event;

import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
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
public class GoodsAuditRejectedEvent extends ApplicationEvent {

    private final GoodsAuditEntity auditEntity;

    public GoodsAuditRejectedEvent(Object source, GoodsAuditEntity auditEntity) {
        super(source);
        this.auditEntity = auditEntity;
    }
}
