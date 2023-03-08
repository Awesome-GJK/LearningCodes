package com.gjk.javabasis.juc.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


/**
 * 对账
 *
 * @author: gaojiankang
 * @date: 2022/11/21 17:33
 * @description:
 */
public class DuiZhang {
    public final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            2,
            4,
            1L,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(16),
            new ThreadFactoryBuilder().setNameFormat("duiZhang-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public static void main(String[] args) throws InterruptedException {
        DuiZhang duiZhang = new DuiZhang();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        AtomicReference<List<String>> pos = new AtomicReference<>();
        AtomicReference<List<String>> dos = new AtomicReference<>();
        duiZhang.EXECUTOR.execute(() -> {
            pos.set(Stream.of("1", "2", "3", "4").collect(Collectors.toList()));
            countDownLatch.countDown();
        });
        duiZhang.EXECUTOR.submit(() -> {
            dos.set(Stream.of("1", "2", "3").collect(Collectors.toList()));
            countDownLatch.countDown();
        });

        countDownLatch.await();

        List<String> list1 = pos.get();
        List<String> list2 = dos.get();
        list1.removeAll(list2);

        System.out.println(JSON.toJSONString(list1));
    }

    //public static void main(String[] args) throws ExecutionException, InterruptedException {
    //    DuiZhang duiZhang = new DuiZhang();
    //
    //    Future<List<String>> future1 = duiZhang.EXECUTOR.submit(() -> {
    //        System.out.println("future1线程");
    //        return Stream.of("1", "2", "3", "4").collect(Collectors.toList());
    //    });
    //    Future<List<String>> future2 = duiZhang.EXECUTOR.submit(() -> {
    //        //countDownLatch.countDown();
    //        System.out.println("future2线程");
    //        return Stream.of("1", "2", "3").collect(Collectors.toList());
    //    });
    //
    //    List<String> list1 = future1.get();
    //    List<String> list2 = future2.get();
    //    list1.removeAll(list2);
    //
    //    System.out.println(JSON.toJSONString(list1));
    //    System.out.println("main线程");
    //}
}
