package com.mmplanet.cloud.app.goods.controller;

import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.common.util.ResultUtils;
import com.mmplanet.cloud.app.goods.api.GoodsDelayedTaskApi;
import com.mmplanet.cloud.app.goods.application.GoodsDelayedTaskApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/22 10:35 <br>
 * @Author: niujiao
 */
@Component
public class GoodsDelayedTaskController implements GoodsDelayedTaskApi {

    @Autowired
    private GoodsDelayedTaskApplication goodsDelayedTaskApplication;

    @Override
    public Response<Boolean> downDeadLinePreSellGoods() {
        return ResultUtils.construct(goodsDelayedTaskApplication.downDeadLinePreSellGoods());
    }
}
