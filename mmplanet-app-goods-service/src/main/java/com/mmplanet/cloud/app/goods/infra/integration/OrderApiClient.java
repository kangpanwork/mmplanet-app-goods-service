package com.mmplanet.cloud.app.goods.infra.integration;

import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.order.api.OrderApi;
import com.mmplanet.cloud.app.order.dto.OrderProofingOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/8 14:07 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class OrderApiClient {

    @Autowired
    private OrderApi orderApi;

    public String getDealerIdBySaasNo(String buyerId, String saasNo) {

        OrderProofingOrderDto dto = new OrderProofingOrderDto();
        dto.setBuyerId(buyerId);
        dto.setProofingCode(saasNo);

        Request<OrderProofingOrderDto> request = Request.<OrderProofingOrderDto>builder().data(dto).build();
        Response<String> response = orderApi.sellerId(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("ORDER-SERVICE sellerId method occur exception,param={},errorMsg={}", saasNo, response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
