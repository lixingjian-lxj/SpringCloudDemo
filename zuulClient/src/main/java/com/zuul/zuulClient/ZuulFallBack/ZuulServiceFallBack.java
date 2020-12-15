package com.zuul.zuulClient.ZuulFallBack;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.CharsetDecoder;

/**
 * @author muyou
 * @date 2020/12/9 15:50
 * Zuul的降级方法
 */
@Component
public class ZuulServiceFallBack implements FallbackProvider {

    @Override
    public String getRoute() {
        //对哪个微服务降级，*表示所有
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println(route+"-----------》进入服务降级");
        if(cause != null && cause.getCause() != null){
            String error = cause.getCause().getMessage();
            System.out.println("异常原因：" + error);
        }
        return response();
    }

    private ClientHttpResponse response(){
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                //状态码
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                //状态码对应的错误信息
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {
                //等getBody方法设置的信息全部返回完（有时候可能返回的比较大），执行close方法
            }

            @Override
            public InputStream getBody() throws IOException {
                //给前端返回具体错误信息
                String s = "服务异常,请稍后再试";
                InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));
                return is;
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
