package com.eveb.bottlepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Lebron on 2018/1/31.
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BottlePayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BottlePayApplication.class, args);
    }
}
