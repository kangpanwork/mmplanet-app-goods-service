package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.notice.api.MessageNoticeApi;
import com.mmplanet.cloud.app.notice.dto.MessageNoticeDto;
import com.mmplanet.cloud.app.notice.enums.NoticeReceiverTypeEnum;
import com.mmplanet.cloud.app.notice.enums.NoticeSenderSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/11 15:54 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class MessageNoticeApiClient {

    @Autowired
    private MessageNoticeApi messageNoticeApi;

    public Boolean sendMessage(MessageNoticeDto dto) {
        dto.setSenderSource(NoticeSenderSourceEnum.GOODS.name());
        dto.setSender(NoticeSenderSourceEnum.GOODS.name());
        dto.setChannel("SYSTEM");
        dto.setReceiverType(NoticeReceiverTypeEnum.PERSON.name());
        Request<MessageNoticeDto> request = new Request<MessageNoticeDto>();
        request.setData(dto);
        Response<Boolean> response = messageNoticeApi.save(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("NOTICE-SERVICE sellerId method occur exception,param={},errorMsg={}", JSON.toJSONString(dto), response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
