package com.mmplanet.cloud.app.goods.application.fixed;

import com.mmplanet.cloud.app.goods.domain.service.impl.GenericServiceImpl;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 17:59 <br>
 * @Author: niujiao
 */
public class DealerShopFactory {

    public static GenericServiceImpl.GenericShopInfoDto build(){
        GenericServiceImpl.GenericShopInfoDto dto = new GenericServiceImpl.GenericShopInfoDto();
        dto.setShopId("21d89fc2a90944a8a533def42a1d9779");
        dto.setShopType("DEALER");
        dto.setStatus("active");
        return dto;
    }
}
