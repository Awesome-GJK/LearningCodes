package com.gjk.jvm.myclassloader;

/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/22 0:54
 * @description:
 */
public class Test {


//    public static void main(String[] args) {
//        try {
//            String path = MyClassLoader1.class.getResource("").getPath();
//            MyClassLoader1 classLoader = new MyClassLoader1(path);
//            Class<?> myClass = classLoader.loadClass("MyClass");
//            System.out.println("我是由"+myClass.getClassLoader().getClass().getName()+"类加载器加载的.");
//            System.out.println("myClass的类加载器的父加载器为：" + myClass.getClassLoader().getParent());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        MyClassLoader2 myClassLoader2 = new MyClassLoader2();
        try {
            Object aClass = myClassLoader2.loadClass("com.gjk.jvm.myclassloader.MyClass");
            System.out.println(aClass.getClass());
            System.out.println(aClass instanceof MyClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
