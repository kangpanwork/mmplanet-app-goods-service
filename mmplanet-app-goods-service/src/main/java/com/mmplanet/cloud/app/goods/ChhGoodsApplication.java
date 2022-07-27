package com.mmplanet.cloud.app.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

/**
 * 启动类
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/12 15:42 <br>
 * @Author: jacky
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mmplanet.cloud.app"})
@ComponentScan(basePackages = {"com.mmplanet.cloud.app"})
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ChhGoodsApplication {

    public static void main(String[] args) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(timeZone);
        SpringApplication.run(ChhGoodsApplication.class, args);
    }

}
