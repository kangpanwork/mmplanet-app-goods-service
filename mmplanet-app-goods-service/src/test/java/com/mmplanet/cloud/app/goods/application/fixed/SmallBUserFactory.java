package com.mmplanet.cloud.app.goods.application.fixed;

import com.mmplanet.cloud.app.user.vo.AppUserVo;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 13:35 <br>
 * @Author: niujiao
 */
public class SmallBUserFactory {

    public static AppUserVo build() {
        AppUserVo appUserVo = new AppUserVo();
        appUserVo.setId("f3f5df8902974dff8f3844fbebdea648");
        appUserVo.setUserType("SMALL_B");
        return appUserVo;
    }
}
