package com.mmplanet.cloud.app.goods.infra.listener;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.goods.domain.event.GoodsAuditRejectedEvent;
import com.mmplanet.cloud.app.goods.domain.service.GoodsAuditService;
import com.mmplanet.cloud.app.goods.domain.service.LocalMessageService;
import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import com.mmplanet.cloud.app.goods.infra.integration.MessageNoticeApiClient;
import com.mmplanet.cloud.app.notice.dto.MessageNoticeDto;
import com.mmplanet.cloud.app.notice.dto.MessageNoticeDto.MessageNoticeMetaData;
import com.mmplanet.cloud.app.notice.enums.NoticeActionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum.REJECT;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/24 18:25 <br>
 * @Author: niujiao
 */
@Component
public class GoodsAuditRejectedEventListener {

    @Autowired
    private GoodsAuditService goodsAuditService;

    @Autowired
    private MessageNoticeApiClient messageNoticeApi;

    @Autowired
    private LocalMessageService localMessageService;

    @EventListener
    public void consume(GoodsAuditRejectedEvent event) {
        GoodsAuditEntity auditEntity = event.getAuditEntity();
        auditEntity.setStatus(REJECT.name());
        auditEntity.setRejectReason(auditEntity.getRejectReason());
        goodsAuditService.updateById(auditEntity);

        GoodsDto goodsAuditReqDto = JSON.parseObject(auditEntity.getDetailContent(), GoodsDto.class);
        sendGoodsRejectNoticeMessage(goodsAuditReqDto.getSmallImage(), auditEntity.getId(), goodsAuditReqDto.getId(), goodsAuditReqDto.getShopId(), goodsAuditReqDto.getUserId());
    }

    public void sendGoodsRejectNoticeMessage(String image, String auditId, String goodsId, String shopId, String receiverId) {
        MessageNoticeDto dto = new MessageNoticeDto();
        dto.setTitle("你的商品审核失败了");
        dto.setImage(image);
        dto.setContent("你的商品审核失败了，去看看哪里出问题...");
        MessageNoticeMetaData messageNoticeMetaData = new MessageNoticeMetaData();
        messageNoticeMetaData.setBusinessType("GOODS_REJECT");
        messageNoticeMetaData.setActionType(NoticeActionTypeEnum.REDIRECT.name());
        messageNoticeMetaData.setActionTypeDesc("去看看");
        Map<String, Object> customParam = new HashMap<String, Object>();
        customParam.put("auditId", auditId);
        customParam.put("goodsId", goodsId);
        customParam.put("shopId", shopId);
        messageNoticeMetaData.setCustomParam(customParam);
        dto.setMetaData(messageNoticeMetaData);
        dto.setReceiver(receiverId);

        LocalMessageEntity messageEntity = new LocalMessageEntity();
        messageEntity.setOpObj("GOODS_AUDIT");
        messageEntity.setOpObjId(auditId);
        messageEntity.setOpEvent("REJECT");
        messageEntity.setTriggerAction("SEND_MESSAGE_NOTICE");
        messageEntity.setTriggerActionParam(JSON.toJSONString(dto));
        localMessageService.save(messageEntity);
    }
}
