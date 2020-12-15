package com.zuul.zuulClient.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;

/**
 * @author muyou
 * @date 2020/12/9 15:25
 * zuulFilter默认的过滤器是：PreDecorationFilter
 */
@Component
public class ZuulServiceFilter extends ZuulFilter {
    /**
     * 过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 同类型过滤器优先级，数值越小优先级越高
     * 如果要获取路由后的地址，必须在Zuul默认的PREFilter之后，因为路由后的地址是在Zuul默认的PREFilter中填充的
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }

    /**
     * 是否启用该过滤器，默认false不启用
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 自定义过滤器的执行逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String s = ctx.get(FilterConstants.REQUEST_URI_KEY).toString();
        System.out.println(request.getRemoteAddr() + "访问了：" + request.getRequestURI() + " 路由后的地址是：" + s);
        return null;
    }
}
