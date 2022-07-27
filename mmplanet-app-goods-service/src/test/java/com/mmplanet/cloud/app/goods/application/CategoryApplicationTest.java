package com.mmplanet.cloud.app.goods.application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mmplanet.cloud.app.goods.dto.AssociateAttrDto;
import com.mmplanet.cloud.app.goods.dto.CategoryAttributeQueryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 13:00 <br>
 * @Author: niujiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryApplicationTest extends BaseTest{

    @Resource
    private CategoryApplication categoryApplication;

    @Resource
    private CategoryAttributeApplication categoryAttributeApplication;


    @Test
    public void save() throws IOException {
        ClassPathResource jsonDataResource = new ClassPathResource("category_add.json");
        String jsonDataText = IOUtils.toString(jsonDataResource.getInputStream(), "utf-8");
        List<CategoryDto> list = JSON.parseObject(jsonDataText, new TypeReference<List<CategoryDto>>() {});
        list.forEach(e ->{
            categoryApplication.save(e);
        });

    }

    @Test
    public void tree(){
        List<CategoryTreeVo> tree = categoryApplication.tree();
        Assert.assertNotNull(!CollectionUtils.isEmpty(tree.get(0).getChildren()));
    }

    @Test
    public void associateAttr() throws IOException {
        ClassPathResource jsonDataResource = new ClassPathResource("associate_attr.json");
        String jsonDataStr = IOUtils.toString(jsonDataResource.getInputStream(), "utf-8");
        List<AssociateAttrDto> list = JSON.parseObject(jsonDataStr, new TypeReference<List<AssociateAttrDto>>() {});
        list.forEach(e ->{
            categoryAttributeApplication.save(e);
        });
    }

    @Test
    public void queryCategoryAttrs() {
        CategoryAttributeQueryDto queryDto = new CategoryAttributeQueryDto();
        queryDto.setCategoryId("35fe4a51136b4988a4affd23da723ee9");
        List<AttributeVo> attributeVos = categoryApplication.queryCategoryAttrs(queryDto);
        Assert.assertTrue(attributeVos.size() == 2);
    }
}