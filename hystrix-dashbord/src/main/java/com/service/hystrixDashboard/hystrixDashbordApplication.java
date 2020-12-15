package com.service.hystrixDashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author muyou
 * @date 2020/12/14 15:34
 */
@SpringBootApplication
@EnableHystrixDashboard
public class hystrixDashbordApplication {

    public static void main(String[] args) {
        SpringApplication.run(hystrixDashbordApplication.class,args);
    }
}
