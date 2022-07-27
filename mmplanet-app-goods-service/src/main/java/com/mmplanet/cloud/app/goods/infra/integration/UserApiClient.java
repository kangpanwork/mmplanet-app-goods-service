package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.user.api.AppUserApi;
import com.mmplanet.cloud.app.user.vo.AppUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户域集成Client
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 13:53 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class UserApiClient {

    @Resource
    private AppUserApi appUserApi;


    public AppUserVo getUser(String userId) {

        BaseIdDto baseIdDto = new BaseIdDto();
        baseIdDto.setId(userId);
        Response<AppUserVo> response = appUserApi.getById(Request.<BaseIdDto>builder().data(baseIdDto).build());
        if (response.success()) {
            return response.getData();
        } else {
            log.error("userService goodsPurchaseNum occur exception,param={},errorMsg={}", JSON.toJSONString(baseIdDto), response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
