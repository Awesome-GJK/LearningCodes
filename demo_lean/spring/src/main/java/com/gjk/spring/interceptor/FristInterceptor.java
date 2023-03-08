package com.gjk.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * FristInterceptor
 *
 * @author: GJK
 * @date: 2021/12/21 14:48
 * @description:
 */
@Component
@Order(1)
public class FristInterceptor implements HandlerInterceptor {

    @Autowired
    private InterceptorService interceptorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("--------------执行FristInterceptor----preHandle方法-----------------");
        interceptorService.a();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("--------------执行FristInterceptor----postHandle方法-----------------");
        interceptorService.a();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("--------------执行FristInterceptor----afterCompletion方法-----------------");
        interceptorService.a();
    }
}
