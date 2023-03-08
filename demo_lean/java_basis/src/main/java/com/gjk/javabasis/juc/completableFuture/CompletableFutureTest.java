package com.gjk.javabasis.juc.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.thread.ThreadFactoryBuilder;

public class CompletableFutureTest {


    private final ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 2,
            1L,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(2000),
            new ThreadFactoryBuilder().setNamePrefix("CompletableFutureTest-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureTest futureTest = new CompletableFutureTest();
        futureTest.testCount();
    }

    public void testCount() throws ExecutionException, InterruptedException {
        //CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 1)
        //        .thenCombineAsync(CompletableFuture.supplyAsync(() -> 2), Integer::sum)
        //        .thenCombineAsync(CompletableFuture.supplyAsync(() -> 3), Integer::sum)
        //        .thenCombineAsync(CompletableFuture.supplyAsync(() -> 4), Integer::sum);
        //Integer integer = future.get();
        //System.out.println(integer);
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> future2= CompletableFuture.supplyAsync(() -> 2);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 3);
        Fruit fruit = Fruit.builder().appleCount(future1.join()).orangeCount(future2.join()).bananaCount(future3.join()).build();
        System.out.println(JSON.toJSONString(fruit));

    }

    public static void commonApi() throws InterruptedException, ExecutionException {
        //supplyAsync:异步执行,并返回结果
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync Test 当前线程是否为守护线程：" + Thread.currentThread().isDaemon());
            return "HELLO";
        });
        System.out.println(future1.join());

        //runAsync:异步执行，不返回结果
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> System.out.println("runAsync Test 当前线程是否为守护线程：" + Thread.currentThread().isDaemon()));
        System.out.println(future2.join());

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "HELLO")
                .thenApply(a -> {
                    return a + " lili!";
                });
        System.out.println(future.get());


        System.out.println("==============================================================================");

        CompletableFuture future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("投放和清洗制作米饭的材料");
            return "干净的没有新冠病毒的大米";
        }).thenAcceptAsync(result -> {
            System.out.println("通电，设定模式，开始煮米饭");
        }).thenRunAsync(() -> {
            System.out.println("米饭做好了，可以吃了");
        });
        future3.get();

        System.out.println("==============================================================================");
        CompletableFuture rice = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始制作米饭，并获得煮熟的米饭");
            return "煮熟的米饭";
        });

        //煮米饭的同时呢，我又做了牛奶
        CompletableFuture mike = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始热牛奶，并获得加热的牛奶");
            return "加热的牛奶";
        });

        // 我想两个都好了，才吃早饭，thenCombineAsync有入参，有返回值
        mike.thenCombineAsync(rice, (m, r) -> {
            System.out.println("我收获了早饭：" + m + "," + r);
            return m + r.toString();
        });

        // 有入参，无返回值
        mike.thenAcceptBothAsync(rice, (m, r) -> {
            System.out.println("我收获了早饭：" + m + "," + r);
        });
        // 无入参，入参会之
        mike.runAfterBothAsync(rice, () -> {
            System.out.println("我收获了早饭");
        });
    }


    public void testRunAsync(){
        List<String> list = Lists.newArrayList();
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println("completableFuture1开始执行----------------");
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add("CompletableFuture1");
            System.out.println("completableFuture1结束执行----------------");
        },executor);

        CompletableFuture<Void> completableFuture2 = CompletableFuture.runAsync(() -> {
            System.out.println("completableFuture2开始执行----------------");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add("CompletableFuture2");
            System.out.println("completableFuture2结束执行----------------");
        },executor);

        CompletableFuture.allOf(completableFuture1,completableFuture2).join();

        System.out.println(JSON.toJSONString(list));



    }

    public void testSupplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture1开始执行----------------");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completableFuture1结束执行----------------");
            return "completableFuture1";
        },executor);

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture2开始执行----------------");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completableFuture2结束执行----------------");
            return "CompletableFuture2";
        },executor);

        CompletableFuture<Object> future = CompletableFuture.anyOf(completableFuture1, completableFuture2);
        System.out.println(future.get().toString());
    }

    public void testThrowException() throws ExecutionException, InterruptedException {
        List<String> list = Stream.of("1", "2", "3", "q", "5").collect(Collectors.toList());
        CompletableFuture[] futures = list.stream().map(str -> CompletableFuture.runAsync(() -> {
            System.out.println(JSON.toJSONString(Stream.of(str).collect(Collectors.toList())));
        })).toArray(CompletableFuture[]::new);
        try {
            CompletableFuture.allOf(futures).join();
        } catch (Exception e) {
            System.out.println("");
        }
        System.out.println("完成！！！！");

    }



}
