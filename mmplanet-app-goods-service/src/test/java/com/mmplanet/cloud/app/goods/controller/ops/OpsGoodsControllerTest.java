package com.mmplanet.cloud.app.goods.controller.ops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmplanet.cloud.app.common.request.Request;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.application.BaseTest;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 20:05 <br>
 * @Author: niujiao
 */
public class OpsGoodsControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void auditPage() throws Exception {


        OpsGoodsAuditListQueryDto dto = new OpsGoodsAuditListQueryDto();
        dto.setAuditStatus(GoodsAuditStatusEnum.TO_AUDIT.name());

        SearchModel<OpsGoodsAuditListQueryDto> searchModel = new SearchModel<OpsGoodsAuditListQueryDto>();
        searchModel.setModel(dto);
        Request<SearchModel<OpsGoodsAuditListQueryDto>> request =
                Request.<SearchModel<OpsGoodsAuditListQueryDto>>builder()
                        .data(searchModel)
                        .build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/ops/goods/goods/audit/page")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void page() {
    }
}