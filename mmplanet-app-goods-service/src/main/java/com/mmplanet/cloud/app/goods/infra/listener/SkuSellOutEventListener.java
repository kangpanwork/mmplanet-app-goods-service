package com.mmplanet.cloud.app.goods.infra.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.goods.application.SkuApplication;
import com.mmplanet.cloud.app.goods.domain.event.SkuSellOutEvent;
import com.mmplanet.cloud.app.goods.domain.service.LocalMessageService;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import com.mmplanet.cloud.app.goods.infra.integration.MessageNoticeApiClient;
import com.mmplanet.cloud.app.goods.vo.FullSkuVo;
import com.mmplanet.cloud.app.notice.dto.MessageNoticeDto;
import com.mmplanet.cloud.app.notice.dto.MessageNoticeDto.MessageNoticeMetaData;
import com.mmplanet.cloud.app.notice.enums.NoticeActionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/24 18:25 <br>
 * @Author: niujiao
 */
@Component
public class SkuSellOutEventListener {

    @Autowired
    private SkuApplication skuApplication;

    @Autowired
    private MessageNoticeApiClient messageNoticeApi;

    @Autowired
    private LocalMessageService localMessageService;

    @EventListener
    public void consume(SkuSellOutEvent event) {
        String skuId = event.getSkuId();

        BaseIdsDto idsDto = new BaseIdsDto();
        idsDto.setIds(Lists.newArrayList(skuId));
        List<FullSkuVo> fullSkuVos = skuApplication.fullDetail(idsDto);
        if (CollectionUtils.isEmpty(fullSkuVos)) {
            return;
        }
        FullSkuVo sku = fullSkuVos.get(0);
        sendSkuSellOutNoticeMessage(sku.getGoodsSmallImage(), sku.getTitle(), sku.getGoodsId(), sku.getId(), sku.getShopId(), sku.getUserId());
    }

    public void sendSkuSellOutNoticeMessage(String image, String goodsId, String title, String skuId, String shopId, String receiverId) {
        MessageNoticeDto dto = new MessageNoticeDto();
        dto.setTitle("商品售罄提醒");
        dto.setImage(image);
        dto.setContent("你的商品#" + title + "#已售罄，快去补货吧");
        MessageNoticeMetaData messageNoticeMetaData = new MessageNoticeMetaData();
        messageNoticeMetaData.setBusinessType("GOODS_SELL_OUT");
        messageNoticeMetaData.setActionType(NoticeActionTypeEnum.REDIRECT.name());
        messageNoticeMetaData.setActionTypeDesc("去店铺");
        Map<String, Object> customParam = new HashMap<String, Object>();
        customParam.put("goodsId", goodsId);
        customParam.put("shopId", shopId);
        customParam.put("skuId", skuId);
        messageNoticeMetaData.setCustomParam(customParam);
        dto.setMetaData(messageNoticeMetaData);
        dto.setReceiver(receiverId);
        messageNoticeApi.sendMessage(dto);

        LocalMessageEntity messageEntity = new LocalMessageEntity();
        messageEntity.setOpObj("SKU");
        messageEntity.setOpObjId(skuId);
        messageEntity.setOpEvent("SELL_OUT");
        messageEntity.setTriggerAction("SEND_MESSAGE_NOTICE");
        messageEntity.setTriggerActionParam(JSON.toJSONString(dto));
        localMessageService.save(messageEntity);
    }
}
