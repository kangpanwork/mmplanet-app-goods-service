package com.mmplanet.cloud.app.goods.application.fixed;

import com.mmplanet.cloud.app.user.vo.AppUserVo;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 13:35 <br>
 * @Author: niujiao
 */
public class DealerUserFactory {

    public static AppUserVo build() {
        AppUserVo appUserVo = new AppUserVo();
        appUserVo.setId("5c5a7c0f02174124bb744032bbbd6642");
        appUserVo.setUserType("DEALER");
        return appUserVo;
    }
}
