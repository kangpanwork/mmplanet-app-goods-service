package com.mmplanet.cloud.app.goods.domain.constraints;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmplanet.cloud.app.goods.infra.entity.AttributeEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.AttributeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/22 18:04 <br>
 * @Author: niujiao
 */
public class AttributeValidation<T extends Annotation> implements ConstraintValidator<T, AttributeEntity> {

    @Autowired
    protected AttributeMapper attributeMapper;

    Predicate<AttributeEntity> predicate = c -> true;

    @Override
    public boolean isValid(AttributeEntity value, ConstraintValidatorContext context) {
        return value == null || predicate.test(value);
    }


    public static class AttributeIdValidator extends AttributeValidation<CheckAttributeIdExist> {

        @Override
        public void initialize(CheckAttributeIdExist constraintAnnotation) {
            predicate = c -> attributeMapper.selectById(c.getId()) != null;
        }
    }

    public static class AttributeNoDuplicationValidator extends AttributeValidation<CheckAttributeNoDuplication> {

        @Override
        public void initialize(CheckAttributeNoDuplication constraintAnnotation) {
            predicate = c -> attributeMapper.selectOne(Wrappers.<AttributeEntity>lambdaQuery().eq(AttributeEntity::getName, c.getName())) == null;
        }
    }
}
