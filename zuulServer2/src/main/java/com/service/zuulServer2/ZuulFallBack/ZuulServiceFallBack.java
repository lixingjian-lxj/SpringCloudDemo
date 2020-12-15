package com.service.zuulServer2.ZuulFallBack;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author muyou
 * @date 2020/12/9 15:50
 * Zuul的降级方法
 */
//@Component
public class ZuulServiceFallBack implements FallbackProvider {

    @Override
    public String getRoute() {
        //对哪个微服务降级，*表示所有
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println(route+"-----------》进入服务降级");
       if(cause instanceof HystrixTimeoutException){
           return response(route,HttpStatus.GATEWAY_TIMEOUT);
       }else {
           return response(route,HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    private ClientHttpResponse response(String route,HttpStatus status){
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                //状态码
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                //状态码对应的错误信息
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
                //等getBody方法设置的信息全部返回完（有时候可能返回的比较大），执行close方法
            }

            @Override
            public InputStream getBody() throws IOException {
                //给前端返回具体错误信息
                return new ByteArrayInputStream("系统繁忙，请稍后再试".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return null;
            }
        };
    }
}
