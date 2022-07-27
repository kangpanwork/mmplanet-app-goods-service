package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.user.api.FavoriteApi;
import com.mmplanet.cloud.app.user.dto.IsFavoriteDto;
import com.mmplanet.cloud.app.user.vo.IsFavoriteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/11 15:43 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class FavoriteApiClient {

    @Autowired
    private FavoriteApi favoriteApi;

    public IsFavoriteVo isFavorite(String userId,String favoriteType,String businessId) {
        IsFavoriteDto isFavoriteDto = new IsFavoriteDto();
        isFavoriteDto.setUserId(userId);
        isFavoriteDto.setFavoriteType(favoriteType);
        isFavoriteDto.setBusinessId(businessId);
        Request<IsFavoriteDto> request = Request.<IsFavoriteDto>builder().data(isFavoriteDto).build();
        Response<IsFavoriteVo> response = favoriteApi.isFavorite(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("isFavorite method occur exception,param={},errorMsg={}", JSON.toJSONString(isFavoriteDto), response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
