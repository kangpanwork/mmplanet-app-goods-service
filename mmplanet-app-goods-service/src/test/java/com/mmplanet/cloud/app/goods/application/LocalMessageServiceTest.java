package com.mmplanet.cloud.app.goods.application;

import com.mmplanet.cloud.app.goods.domain.service.LocalMessageService;
import com.mmplanet.cloud.app.goods.infra.entity.LocalMessageEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/11 20:18 <br>
 * @Author: niujiao
 */
public class LocalMessageServiceTest extends BaseTest{

    @Autowired
    private LocalMessageService localMessageService;

    @Test
    public void save() throws InterruptedException {
        LocalMessageEntity messageEntity = localMessageService.getById(7);
        localMessageService.save(messageEntity);
        Thread.sleep(100000L);
    }
}