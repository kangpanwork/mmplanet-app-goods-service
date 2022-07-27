package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.Page;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import com.mmplanet.cloud.app.user.api.UserBlacklistApi;
import com.mmplanet.cloud.app.user.dto.UserBlacklistQueryDto;
import com.mmplanet.cloud.app.user.vo.ShopVo;
import com.mmplanet.cloud.app.user.vo.UserBlacklistVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/23 17:33 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class UserBlacklistApiClient {

    @Autowired
    private UserBlacklistApi userBlacklistApi;

    public PageData<UserBlacklistVo> page(Integer pageNum, Integer pageSize, UserBlacklistQueryDto queryDto) {

        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        SearchModel<UserBlacklistQueryDto> searchModel = new SearchModel<UserBlacklistQueryDto>();
        searchModel.setPage(page);
        searchModel.setModel(queryDto);

        Request<SearchModel<UserBlacklistQueryDto>> request = Request.<SearchModel<UserBlacklistQueryDto>>builder().data(searchModel).build();
        Response<PageData<UserBlacklistVo>> response = userBlacklistApi.page(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("getShop method occur exception,pageNum={},pageSize={},param={},errorMsg={}", pageNum, pageSize, JSON.toJSONString(queryDto), response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
