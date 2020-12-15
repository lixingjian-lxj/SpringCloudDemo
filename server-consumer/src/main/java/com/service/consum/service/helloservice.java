package com.service.consum.service;

import com.service.consum.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author muyou
 * @date 2020/12/1 15:00
 * feign整合hystrix：@FeignClient(name = "server-provider01",fallback = helloservicefallBack.class)
 *
 * 单独：@FeignClient(value = "server-provider01")
 */
@FeignClient(name = "provider-server0",fallback = helloservicefallBack.class)
public interface helloservice {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String hello();


    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    String hello2(@RequestParam("name") String name);

    @RequestMapping(value = "/hello3",method = RequestMethod.POST)
    String hello3(@RequestBody User user);
}
