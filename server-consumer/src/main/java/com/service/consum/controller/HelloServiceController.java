package com.service.consum.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.service.consum.entity.User;
import com.service.consum.service.helloservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloServiceController {

    @Autowired
    helloservice helloservice;

    @RequestMapping(value = "/feign-consumer",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getFeignPowerFullBack")
    public String hello(){
        return helloservice.hello();
    }


    /**
     * fallbackMethod 服务降级回调方法
     * commandKey 命令名称
     * groupKey 分组
     * threadPoolKey 线程池划分
     *直接加在方法上的注解比加在配置文件中的优先级高
     * @param name
     * @return
     */
    @RequestMapping(value = "/feign-consumer2",method = RequestMethod.GET)
    public String hello2(@RequestParam("name") String name){
        return helloservice.hello2(name);
    }

    @RequestMapping(value = "/feign-consumer3",method = RequestMethod.GET)
    public String hello3(@RequestBody User user){
        return helloservice.hello3(user);
    }

    /**
     * 每个方法的fallbackMethod不同，如果该方法无参那么就会去找注解中指定的无参的fallbackMethod方法
     * @return
     */
    public String getFeignPowerFullBack(){
        return "系统正在维护中，请稍后重试！！！";
    }
}
