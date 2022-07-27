package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.shoppingcard.api.ShoppingCardApi;
import com.mmplanet.cloud.app.shoppingcard.dto.ShoppingCardPurchaseNumQueryDto;
import com.mmplanet.cloud.app.shoppingcard.vo.ShoppingCardPurchaseNumVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车域集成Client
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/24 15:53 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class ShoppingCardApiClient {

    @Resource
    ShoppingCardApi shoppingCardApi;

    public List<ShoppingCardPurchaseNumVo> goodsPurchaseNum(List<String> goodsIds) {

        ShoppingCardPurchaseNumQueryDto queryDto = new ShoppingCardPurchaseNumQueryDto();
        queryDto.setGoodsIds(goodsIds);
        Request<ShoppingCardPurchaseNumQueryDto> request = Request.<ShoppingCardPurchaseNumQueryDto>builder()
                .data(queryDto).build();
        Response<List<ShoppingCardPurchaseNumVo>> response = shoppingCardApi.goodsPurchaseNum(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("shoppingCard goodsPurchaseNum occur exception,param={},errorMsg={}", JSON.toJSONString(goodsIds),response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
