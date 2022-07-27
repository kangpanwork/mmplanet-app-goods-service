package com.mmplanet.cloud.app.goods.application;

import com.mmplanet.cloud.app.goods.ChhGoodsApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/31 10:12 <br>
 * @Author: niujiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChhGoodsApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseTest {

}
