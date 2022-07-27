package com.mmplanet.cloud.app.goods.controller;

import com.mmplanet.cloud.app.goods.application.AttributeApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品属性表 管理
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Slf4j
@RestController
@RequestMapping("/app/goods/attribute")
public class AttributeController {

    @Autowired
    private AttributeApplication attributeApplication;
}
