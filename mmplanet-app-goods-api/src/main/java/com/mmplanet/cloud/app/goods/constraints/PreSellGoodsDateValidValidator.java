package com.mmplanet.cloud.app.goods.constraints;

import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/28 11:21 <br>
 * @Author: niujiao
 */
public class PreSellGoodsDateValidValidator implements ConstraintValidator<PreSellGoodsDateValid, GoodsDto> {

    @Override
    public boolean isValid(GoodsDto goodsDto, ConstraintValidatorContext constraintValidatorContext) {
        if (goodsDto == null) {
            return true;
        }
        String goodsTypeCode = goodsDto.getGoodsTypeCode();
        if (!GoodsTypeEnum.PRE_SELL.name().equals(goodsTypeCode)) {
            return true;
        }
        Date activityEndTime = goodsDto.getActivityEndTime();
        Date activityStartTime = goodsDto.getActivityStartTime();
        if(activityEndTime == null || activityStartTime == null){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate("预售日期必填")
                    .addConstraintViolation();
            return false;
        }
        return activityEndTime.compareTo(activityStartTime) > 0;
    }
}
