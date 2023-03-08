package com.gjk.javabasis.juc.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

/**
 * SemaphoreMutex
 *
 * @author: gaojiankang
 * @date: 2022/11/14 17:34
 * @description:
 */
public class SemaphoreMutex {

    private int count;

    /**
     * 运行通行数量为1的信号量
     */
    private static final Semaphore semaphore = new Semaphore(1);

    public void addOne(int i) {
        try {
            semaphore.acquire();
            System.out.println("Thread " + i + " 获取互斥锁");
            count += 1;
            Thread.sleep(1000);
            System.out.println("Thread " + i + " 当前结果为：" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println("Thread " + i + " 释放互斥锁");
        }
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SemaphoreMutex semaphoreMutex = new SemaphoreMutex();
        List<Integer> list = new ArrayList<>();
        for(int i = 1 ; i<=1000; i++){
            list.add(i);
        }
        CompletableFuture[] array = list.stream().map(item -> CompletableFuture.runAsync(() -> semaphoreMutex.addOne(item)))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(array).join();

        long end = System.currentTimeMillis();
        System.out.println("总耗时为：" + (end - start));
        System.out.println("最后结果为：" + semaphoreMutex.count);
    }
}
