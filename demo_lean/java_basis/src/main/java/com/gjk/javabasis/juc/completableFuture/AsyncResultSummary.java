package com.gjk.javabasis.juc.completableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.hutool.core.thread.ThreadFactoryBuilder;

/**
 * @Classname AsyncResultSummary
 * @Description 多个异步结果汇总
 * @Date 2024/2/2 16:41
 * @Created by gaojiankang
 */
public class AsyncResultSummary {

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

        CompletableFuture<Integer> oneFuture = CompletableFuture.supplyAsync(() -> 1, executor);
        CompletableFuture<Integer> twoFuture = CompletableFuture.supplyAsync(() -> 2, executor);
        CompletableFuture<Integer> threeFuture = CompletableFuture.supplyAsync(() -> 3, executor);

        //获取异步返回结果，并求和
        List<CompletableFuture<Integer>> futureList = Stream.of(oneFuture, twoFuture, threeFuture).collect(Collectors.toList());
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
                .thenApply(v -> futureList.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum());

        // 打印结果
        System.out.println(integerCompletableFuture.join());
    }
}
