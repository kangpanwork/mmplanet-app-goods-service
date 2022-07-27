package com.mmplanet.cloud.app.goods.infra.listener;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.goods.domain.event.LocalMessageCreatedEvent;
import com.mmplanet.cloud.app.goods.domain.service.LocalMessageService;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import com.mmplanet.cloud.app.goods.infra.integration.MessageNoticeApiClient;
import com.mmplanet.cloud.app.notice.dto.MessageNoticeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/11 19:04 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class LocalMessageCreatedEventListener {

    @Autowired
    private MessageNoticeApiClient messageNoticeApi;

    @Autowired
    private LocalMessageService localMessageService;

    @Async
    @TransactionalEventListener
    public void consume(LocalMessageCreatedEvent event) {
        log.info("consume localMessage...");
        LocalMessageEntity localMessageEntity = event.getLocalMessageEntity();
        try {
            // 目前不需要根据类型判断
            MessageNoticeDto dto = JSON.parseObject(localMessageEntity.getTriggerActionParam(), MessageNoticeDto.class);
            dto.setBusinessId(localMessageEntity.getMsgId());
            Boolean result = messageNoticeApi.sendMessage(dto);

            localMessageEntity.setStatus(result ? 1 : -1);
            localMessageEntity.setUpdateTime(new Date());
            localMessageService.updateById(localMessageEntity);
        } catch (Exception e) {
            log.error("send message notice,param={}", JSON.toJSONString(localMessageEntity), e);
            localMessageEntity.setStatus(-1);
            localMessageEntity.setUpdateTime(new Date());
            localMessageService.updateById(localMessageEntity);
        }
    }
}
