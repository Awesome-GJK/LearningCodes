package com.gjk.javabasis.juc.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ThreadFactoryBuilder;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2024-08-09 11:25
 **/
public class test {

    private static ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            30L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500),
            new ThreadFactoryBuilder().setNamePrefix("order-feign").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) {

        CompletableFuture<Void> oneFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("runAsync :" + Thread.currentThread().getName());
            throw new RuntimeException("异步异常");
        }, executor).whenComplete((r, e) -> {
            if (e != null) {
                System.out.println("whenComplete ：" + Thread.currentThread().getName() + "，异常信息：" + e.getMessage());
            } else {
                System.out.println("oneFuture 正常：" + r);
            }
        });
        oneFuture.join();
        System.out.println("main :" + Thread.currentThread().getName());


    }
}
