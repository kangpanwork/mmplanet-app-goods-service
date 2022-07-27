package com.mmplanet.cloud.app.goods.infra.integration;

import com.alibaba.fastjson.JSON;
import com.mmplanet.cloud.app.comment.api.ProductCommentApi;
import com.mmplanet.cloud.app.comment.dto.ProductCommentPageQueryDto;
import com.mmplanet.cloud.app.comment.vo.ProductCommentVo;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.Page;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/25 14:16 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class GoodsCommentApiClient {

    @Autowired
    private ProductCommentApi productCommentApi;

    public PageData<ProductCommentVo> page(Integer pageNum, Integer pageSize, String goodsId) {
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        ProductCommentPageQueryDto queryDto = new ProductCommentPageQueryDto();
        queryDto.setGoodsId(goodsId);

        SearchModel<ProductCommentPageQueryDto> searchModel = new SearchModel<ProductCommentPageQueryDto>();
        searchModel.setPage(page);
        searchModel.setModel(queryDto);

        Request<SearchModel<ProductCommentPageQueryDto>> request = Request.<SearchModel<ProductCommentPageQueryDto>>builder()
                .data(searchModel).build();

        Response<PageData<ProductCommentVo>> response = productCommentApi.listByScore(request);
        if (response.success()) {
            return response.getData();
        } else {
            log.error("commentService page method occur exception,pageNum={},pageSize={},param={},errorMsg={}",
                    pageNum,
                    pageSize,
                    goodsId, response.getMessage());
            throw new BaseException(BaseCode.FAIL);
        }
    }
}
