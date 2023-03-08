package com.gjk.spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gjk.spring.interceptor.InterceptorService;

/**
 * MyFilter
 *
 * @author: GJK
 * @date: 2022/7/11 18:37
 * @description:
 */
@Component
@Order(1)
@WebFilter(urlPatterns = "/test/**")
public class MyFilter1 implements Filter {

    @Autowired
    private InterceptorService interceptorService;

    /**
     * Filter初始化的时候调用该方法
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        interceptorService.a();
        System.out.println("MyFilter1 has bean initialized...");
    }

    /**
     * 每个用户请求都会调用这个方法，校验通过则放行到下个过滤器
     * 等请求通过所有过滤器后才会到达servlet
     * @param request
     * @param response
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("MyFilter1#doFilter方法开始执行，对" + ((HttpServletRequest)request).getRequestURI().toString() + "请求进行过来...");
        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + response.getContentType());
        interceptorService.a();
        chain.doFilter(request,response);
        System.out.println("尝试获取contentType如下： " + response.getContentType());
        System.out.println("检验接口是否被调用，MyFilter1#doFilter执行结束");

    }

    @Override
    public void destroy() {
        System.out.println("MyFilter1 has been destroyed...");
    }
}
