package com.gjk.jvm.myclassloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * MyClassLoader1
 * 自定义类加载器
 *
 * @author: GJK
 * @date: 2022/6/22 0:32
 * @description:
 */
public class MyClassLoader1 extends ClassLoader {

    /**
     *  定义一个存放字节码的地址
     */
    private String codePath;

    public MyClassLoader1(ClassLoader parent, String codePath) {
        super(parent);
        this.codePath = codePath;
    }

    public MyClassLoader1(String codePath) {
        this.codePath = codePath;
    }


    /**
     * 重写findClass,不会破坏双亲委派机制
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String file = codePath + name + ".class";
        BufferedInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            byte[] array = outputStream.toByteArray();
            return  defineClass(null,array,0,array.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
