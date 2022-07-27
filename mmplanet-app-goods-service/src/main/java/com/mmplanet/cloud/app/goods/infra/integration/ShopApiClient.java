package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.user.api.ShopApi;
import com.mmplanet.cloud.app.user.vo.ShopVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/11 15:51 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class ShopApiClient {

    @Autowired
    private ShopApi shopApi;

    public ShopVo getShop(String shopId) {

        BaseIdDto baseIdDto = new BaseIdDto();
        baseIdDto.setId(shopId);
        Request<BaseIdDto> request = Request.<BaseIdDto>builder().data(baseIdDto).build();

        Response<ShopVo> response = shopApi.getById(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("getShop method occur exception,param={},errorMsg={}", shopId, response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }

    /**
     * 获取店铺信息
     *
     * @param userId
     * @return
     */
    public ShopVo getShopByUserId(String userId) {
        Request<String> request = Request.<String>builder().data(userId).build();
        Response<ShopVo> response = shopApi.getByUserId(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("getByUserId method occur exception,param={},errorMsg={}", userId, response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }

}
