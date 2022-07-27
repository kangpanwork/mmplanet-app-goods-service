package com.mmplanet.cloud.app.goods.application.fixed;

import com.mmplanet.cloud.app.goods.domain.service.impl.GenericServiceImpl;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 17:59 <br>
 * @Author: niujiao
 */
public class SmallBShopFactory {

    public static GenericServiceImpl.GenericShopInfoDto build(){
        GenericServiceImpl.GenericShopInfoDto dto = new GenericServiceImpl.GenericShopInfoDto();
        dto.setShopId("b2a13c6016fe42538d4bb0d27c9fb2c4");
        dto.setShopType("SMALL_B");
        dto.setStatus("active");
        return dto;
    }
}
