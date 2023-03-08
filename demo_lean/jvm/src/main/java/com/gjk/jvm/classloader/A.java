package com.gjk.jvm.classloader;

/**
 * A
 *
 * @author: GJK
 * @date: 2022/3/28 11:30
 * @description:
 */
public class A {

    public void sayHi(){
        System.out.println("我是A,我是被" + A.class.getClassLoader() + "加载的");
    }
}
