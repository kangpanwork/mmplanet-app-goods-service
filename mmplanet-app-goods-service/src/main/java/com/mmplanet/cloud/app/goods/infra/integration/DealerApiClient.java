package com.mmplanet.cloud.app.goods.infra.integration;

import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.user.api.DealerApi;
import com.mmplanet.cloud.app.user.dto.DealerDto;
import com.mmplanet.cloud.app.user.vo.DealerVo;
import com.mmplanet.cloud.app.user.vo.ShopVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/11 15:51 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class DealerApiClient {

    @Autowired
    private DealerApi dealerApi;

    public DealerVo getDealer(String shopId) {

        BaseIdDto baseIdDto = new BaseIdDto();
        baseIdDto.setId(shopId);
        Request<BaseIdDto> request = Request.<BaseIdDto>builder().data(baseIdDto).build();

        Response<DealerVo> response = dealerApi.getById(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("getDealer method occur exception,param={},errorMsg={}", shopId, response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }


    public DealerVo getDealerByUserId(String userId) {

        DealerDto dto = new DealerDto();
        dto.setUserId(userId);
        Request<DealerDto> request =  Request.<DealerDto>builder().data(dto).build();
        Response<List<DealerVo>> response = dealerApi.list(request);
        if (response.success()) {
            List<DealerVo> data = response.getData();
            return CollectionUtils.isEmpty(data) ? null : data.get(0);
        } else {
            log.error("getDealer method occur exception,param={},errorMsg={}", userId, response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
