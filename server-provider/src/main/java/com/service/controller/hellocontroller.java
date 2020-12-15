package com.service.controller;

import com.service.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class hellocontroller {

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/hello" , method = RequestMethod.GET)
    public String Hello()
    {
        return "hello 我的端口号是："+port;
    }

    @RequestMapping(value = "/hello2" , method = RequestMethod.GET)
    public String Hello2(@RequestParam("name") String name) throws Exception {
        //模拟服务降级
        /*if("lixingjian".equals(name)){
            throw new Exception("模拟服务降级，测试异常");
        }*/
        //模拟响应过慢,默认熔断器延迟时间是2秒
        //TimeUnit.SECONDS.sleep(3);
        return "hello" + name + "我的端口号是："+port;
    }

    @RequestMapping(value = "/hello3" , method = RequestMethod.POST)
    public String Hello3(@RequestBody User user)
    {
        return "hello" + user.getName() + " : " + user.getAge() + "我的端口号是："+port;
    }

}
