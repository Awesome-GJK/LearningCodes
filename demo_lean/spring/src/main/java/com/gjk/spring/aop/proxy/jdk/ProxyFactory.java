package com.gjk.spring.aop.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.gjk.spring.aop.proxy.RealSubject;
import com.gjk.spring.aop.proxy.Subject;

/**
 * ProxyFactory
 *
 * JDK动态代理实现需要接口，通过Proxy.newProxyInstance创建代理对象，通过实现InvocationHandler接口+反射完成代理对象的方法调用
 *
 * @author: GJK
 * @date: 2022/3/31 17:46
 * @description:
 */
public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("计算开始时间");
                System.out.println(proxy.getClass().getSimpleName());
                method.invoke(target,args);
                System.out.println("计算结束时间");
                return null;
            }
        });
    }


    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        System.out.println("原始类" + realSubject.getClass());
        Subject instance = (Subject)new ProxyFactory(realSubject).getProxyInstance();
        System.out.println("代理类" + instance.getClass());
        instance.request();
    }
}
