package com.mmplanet.cloud.app.goods.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/28 11:21 <br>
 * @Author: niujiao
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PreSellGoodsDateValidValidator.class })
@Documented
public @interface PreSellGoodsDateValid {

    String message() default "The End date should be after the starting date." ;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
