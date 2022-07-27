package com.mmplanet.cloud.app.goods.application;

import com.mmplanet.cloud.app.goods.application.fixed.AttributeDtoFactory;
import com.mmplanet.cloud.app.goods.dto.AttributeDto;
import com.mmplanet.cloud.app.goods.dto.DeleteAttributeDto;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/31 10:12 <br>
 * @Author: niujiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttributeApplicationTest extends BaseTest{

    @Resource
    private AttributeApplication attributeApplication;

    @Test(expected = ConstraintViolationException.class)
    public void should_return_failure_when_save_given_attr_duplication() throws IOException {
        List<AttributeDto> list = AttributeDtoFactory.build();
        AttributeDto attributeDto = list.get(0);
        attributeApplication.save(attributeDto);
        attributeApplication.save(attributeDto);
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_return_failure_when_save_given_attr_id_not_exist() throws IOException {
        List<AttributeDto> list = AttributeDtoFactory.build();
        AttributeDto attributeDto = list.get(0);
        String id = attributeApplication.save(attributeDto);
        attributeDto.setId(id+"-");
        attributeApplication.save(attributeDto);
    }

    @Test
    public void should_return_success_when_delete_given_correct_arg() throws IOException {
        List<AttributeDto> list = AttributeDtoFactory.build();
        AttributeDto attributeDto = list.get(0);
        attributeDto.setName(attributeDto.getName() + RandomUtils.nextInt());
        String id = attributeApplication.save(attributeDto);

        DeleteAttributeDto dto = new DeleteAttributeDto();
        dto.setId(id);
        dto.setOpUserId("system");
        Boolean result = attributeApplication.delete(dto);
        Assert.assertTrue(result);
    }
}