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

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * MyFilter2
 *
 * @author: GJK
 * @date: 2022/7/12 9:42
 * @description:
 */
@Component
@Order(2)
@WebFilter(urlPatterns = "/test/**")
public class MyFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter2#doFilter方法开始执行，对" + ((HttpServletRequest)request).getRequestURI().toString() + "请求进行过来...");
        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + response.getContentType());
        chain.doFilter(request,response);
        System.out.println("尝试获取contentType如下： " + response.getContentType());
        System.out.println("检验接口是否被调用，MyFilter2#doFilter执行结束");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter2 has bean initialized...");
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter2 has been destroyed...");
    }
}
