package com.service.consum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableEurekaClient注解已经包含了@EnableDiscoveryClient注解的功能,该注解使得服务调用者有能力去Eureka中发现服务
 *
 * @EnableHystrix老版本必须加@EnableCircuitBreaker注解开启熔断等功能（新版本已经被@EnableHystrix注解中有了，所以这一个注解就够了）
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class ConsumApplication{

	public static void main(String[] args) {
		SpringApplication.run(ConsumApplication.class, args);
	}


	@Bean
	@LoadBalanced//开启了负载均衡
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}

	//修改负载均衡的策略
/*	@Bean
	public IRule iRule(){
		//修改负载均衡策略为随机
		return new RandomRule();
	}*/
}
