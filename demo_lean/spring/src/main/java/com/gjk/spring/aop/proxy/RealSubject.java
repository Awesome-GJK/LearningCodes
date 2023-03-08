package com.gjk.spring.aop.proxy;

/**
 * RealSubject
 *
 * @author: GJK
 * @date: 2022/3/31 17:46
 * @description:
 */
public class RealSubject implements Subject{
    @Override
    public void request() {
        System.out.println("卖房");
    }
}
