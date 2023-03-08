package com.gjk.jvm.classloader;

/**
 * action
 *
 * 类加载器
 * BootstrapClassLoader 启动类加载器，加载路径jre/lib，java虚拟机启动后创建的第一个类加载器，由C++语言实现，所以我们在java代码中查看其信息时，看到的均为null
 * ExtClassLoader 扩展类加载器，加载路径jre/lib/ext
 * AppClassLoader 应用程序类加载器，加载路径classpath
 *
 * 关系：
 * AppClassLoader的parent属性 = ExtClassLoader
 * ExtClassLoader的parent属性 = BootstrapClassLoader
 *
 * 类加载双亲委派机制：
 * 当前类加载器(以系统类加载器为例)在加载一个类时，委托给其双亲（注意这里的双亲指的是类加载器中parent属性指向的类加载器）先进行加载
 *
 * 双亲委派机制避免类被重复加载，保障了核心类的安全
 *
 *
 * @author: GJK
 * @date: 2022/3/23 18:04
 * @description:
 */
public class Action {


    public static void main(String[] args) {
        ClassLoader systemLoader = Action.class.getClassLoader();
        System.out.println("应用程序类加载器：：：：" + systemLoader);

        ClassLoader extLoader = systemLoader.getParent();
        System.out.println("扩展类加载器：：：：" + extLoader);

        ClassLoader bootLoader = extLoader.getParent();
        System.out.println("启动类加载器：：：：" + bootLoader);

    }


    public static void main1(String[] args) {
        System.out.println("我是Action,我是被" + Action.class.getClassLoader() + "加载的");
        A a = new A();
        a.sayHi();
    }


}
