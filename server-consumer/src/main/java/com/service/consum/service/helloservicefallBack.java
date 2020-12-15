package com.service.consum.service;

import com.service.consum.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author muyou
 * @date 2020/12/2 17:42
 *必须加@Component注解加入spring管理，不然不起作用
 * feign整合hystrix
 */

@Component
public class helloservicefallBack implements helloservice {

    @Override
    public String hello() {
        return "hello服务暂时不可用";
    }

    @Override
    public String hello2(String name) {
        return "hello2服务暂时不可用";
    }


    @Override
    public String hello3(User user) {
        return "hello3服务暂时不可用";
    }
}
