package com.gjk.javabasis.juc.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;


/**
 * ObjectPool
 * 限流器
 *
 * @author: gaojiankang
 * @date: 2022/11/14 19:27
 * @description:
 */
public class ObjectPool<T,R> {

    private final List<T> pool;

    private final Semaphore semaphore1;




    /**
     * 构造方法，用于初始化对象池
     * @param size
     * @param t
     */
    public ObjectPool(int size, T t) {
        this.pool = new Vector<T>();
        for(int i=0;i<size;i++){
            this.pool.add(t);
        }
        this.semaphore1 = new Semaphore(size);
    }



    public R exec(Function<T,R> function) throws InterruptedException {
        semaphore1.acquire();
        T t =null;
        try {
            t = pool.remove(0);
            return function.apply(t);
        } finally {
            pool.add(t);
            semaphore1.release();
        }
    }


    public static void main(String[] args) {
        ObjectPool<Long, String> pool = new ObjectPool<Long, String>(10, 2L);
        try {
            pool.exec(t -> {
                System.out.println(t);
                return t.toString();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
