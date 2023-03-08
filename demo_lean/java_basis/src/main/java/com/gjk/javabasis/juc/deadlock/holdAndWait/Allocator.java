package com.gjk.javabasis.juc.deadlock.holdAndWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Allocator 分配器
 *
 * @author: gaojiankang
 * @date: 2022/11/7 16:44
 * @description:
 */
public class Allocator<T> {

    private static volatile Allocator<Object> allocator = null;

    private List<T> list = new ArrayList<>();

    private Allocator() {
    }

    /**
     * 一次性申请所有资源
     * @param from
     * @param to
     * @return
     */
    synchronized void apply(T from, T to) throws InterruptedException {
        while (list.contains(from) || list.contains(to)){
            //不满足条件，进入等待队列
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //获取两个资源
        list.add(from);
        list.add(to);
    }

    /**
     * 一次性释放所有资源
     * @param from
     * @param to
     */
    synchronized void free(T from, T to){
        list.remove(from);
        list.remove(to);
        notifyAll();
    }

    public static <T> Allocator<T> getInstance(){
        if(allocator == null){
            synchronized (Allocator.class){
                if(allocator == null){
                    allocator = new Allocator<>();
                }
            }
        }
        return (Allocator<T>) allocator;
    }
}
