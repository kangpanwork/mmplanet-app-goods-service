package com.mmplanet.cloud.app.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/21 14:40 <br>
 * @Author: niujiao
 */
public class Test {

    public static void main(String[] args) throws IOException {
        ClassPathResource jsonDataResource = new ClassPathResource("省市区.json");
        String jsonData = IOUtils.toString(jsonDataResource.getInputStream(), "utf-8");
        List<Item> items = JSON.parseObject(jsonData, new TypeReference<List<Item>>(){});

        List<City> data = new ArrayList<>();
        items.forEach(item -> {
            City city = new City();
            city.setTitle(item.value);
            city.setValue(item.code);

            List<Item> childList = item.getChildList();
            if(!CollectionUtils.isEmpty(childList)){
                city.setCities(childList.stream().map(child -> {
                    City c = new City();
                    c.setTitle(child.value);
                    c.setValue(child.code);
                    return c;
                }).collect(Collectors.toList()));
            }
            data.add(city);
        });
        System.out.println(JSON.toJSONString(data));
    }


    @Data
    public static class Item{
        private String code;
        private String value;
        private List<Item> childList;
    }

    @Data
    public static class City{
        private String value;
        private String title;
        private List<City> cities;
    }
}
