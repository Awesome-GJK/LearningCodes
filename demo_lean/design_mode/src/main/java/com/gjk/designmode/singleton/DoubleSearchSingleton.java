package com.gjk.designmode.singleton;

/**
 * DoubleSearchSingleton
 *
 * @author: GJK
 * @date: 2022/8/14 21:35
 * @description:
 */
public class DoubleSearchSingleton {
    private DoubleSearchSingleton() {
    }

    static volatile DoubleSearchSingleton instance;

    static DoubleSearchSingleton getInstance(){
        if(instance == null){
           synchronized (DoubleSearchSingleton.class){
               if(instance == null){
                   instance = new DoubleSearchSingleton();
               }
           }
        }
        return instance;
    }
}
