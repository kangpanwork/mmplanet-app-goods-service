package com.mmplanet.cloud.app.goods.application.fixed;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mmplanet.cloud.app.goods.dto.AttributeDto;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/22 18:34 <br>
 * @Author: niujiao
 */
public class AttributeDtoFactory {

    public static List<AttributeDto> build() throws IOException {
        ClassPathResource jsonDataResource = new ClassPathResource("attrs_add.json");
        String jsonDataText = IOUtils.toString(jsonDataResource.getInputStream(), "utf-8");
        return JSON.parseObject(jsonDataText, new TypeReference<List<AttributeDto>>() {
        });
    }
}
