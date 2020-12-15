package com.service02.controller;

import com.service02.entity.User;
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
        return "hello" + name + "我的端口号是："+port;
    }

    @RequestMapping(value = "/hello3" , method = RequestMethod.POST)
    public String Hello3(@RequestBody User user)
    {
        return "hello" + user.getName() + " : " + user.getAge() + "我的端口号是："+port;
    }
}
