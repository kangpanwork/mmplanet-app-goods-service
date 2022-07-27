package com.mmplanet.cloud.app.goods.domain.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/22 18:03 <br>
 * @Author: niujiao
 */
@Target({TYPE, ANNOTATION_TYPE,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AttributeValidation.AttributeNoDuplicationValidator.class})
@Documented
public @interface CheckAttributeNoDuplication {

    String message() default "属性名已存在！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
