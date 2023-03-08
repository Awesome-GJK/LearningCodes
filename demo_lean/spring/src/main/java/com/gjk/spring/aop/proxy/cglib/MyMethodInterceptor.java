package com.gjk.spring.aop.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * MyMethodInterceptor
 *
 * @author: GJK
 * @date: 2022/3/31 18:18
 * @description:
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("目标类增强前！！！");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("目标类增强后！！！");
        return object;
    }
}
