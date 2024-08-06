package com.gjk.javabasis.juc.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ThreadFactoryBuilder;

/**
 * @author: gaojiankang
 * @Desc: 两个任务串行，并且join需要等两个任务完成
 * @create: 2024-08-06 23:34
 **/
public class TwoTaskSerialable {

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
            System.out.println(1);
        }, executor);
        CompletableFuture<Void> twoFuture = oneFuture.thenRunAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(2);
        }, executor);

        CompletableFuture.allOf(oneFuture, twoFuture).join();
        System.out.println(3);
    }
}
