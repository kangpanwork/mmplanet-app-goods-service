package com.mmplanet.cloud.app.goods.api;

import com.mmplanet.cloud.app.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/22 10:31 <br>
 * @Author: niujiao
 */
@FeignClient(path = "/app/goods/delayedTask", value = "${mmplanet.service.name:mmplanet-app-goods}")
public interface GoodsDelayedTaskApi {

    /**
     * 下架活动结束预售商品
     *
     * @return
     */
    Response<Boolean> downDeadLinePreSellGoods();
}
