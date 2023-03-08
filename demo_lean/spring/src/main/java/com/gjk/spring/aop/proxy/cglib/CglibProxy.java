package com.gjk.spring.aop.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;

import com.gjk.spring.aop.proxy.RealSubject;

/**
 * CglibProxy
 *
 *  Cglib代理实现是通过创建Enhancer对象 + 创建MethodInterceptor实现类 完成的，将Enhancer的superclass属性设置为委托类，Enhancer设置回调函数为MethodInterceptor实现类 ，
 *  调用Enhancer.create()方法获取代理对象。
 *
 *   CGlib 采用了FastClass 的机制来实现对被拦截方法的调用。FastClass 机制就是对一个类的方法建立索引，通过索引来直接调用相应的方法
 * @author: GJK
 * @date: 2022/3/31 18:52
 * @description:
 */
public class CglibProxy {

    public static void main(String[] args) {
        //创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件
        enhancer.setSuperclass(RealSubject.class);
        //设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());
        //这里的creat方法就是正式创建代理类
        RealSubject realSubject = (RealSubject) enhancer.create();
        realSubject.request();
    }
}
