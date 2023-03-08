package com.gjk.jvm.myclassloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyClassLoader2
 *
 * @author: GJK
 * @date: 2022/6/22 1:18
 * @description:
 */
public class MyClassLoader2 extends ClassLoader{
    /**
     * 重写loadClass，但是会破坏双亲委派机制
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String className = name.substring(name.lastIndexOf(".") + 1) + ".class";
        InputStream inputStream = null;
        try {
            inputStream = getClass().getResourceAsStream(className);
            if(inputStream == null){
               return super.loadClass(name);
            }
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            return defineClass(name, bytes, 0, available);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
