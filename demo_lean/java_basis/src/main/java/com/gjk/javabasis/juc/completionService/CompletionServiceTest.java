package com.gjk.javabasis.juc.completionService;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ThreadFactoryBuilder;

/**
 * CompletionServiceTest
 *
 * @author: gaojiankang
 * @date: 2023/1/5 15:44
 * @description:
 */
public class CompletionServiceTest {

    private final ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            30L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50),
            new ThreadFactoryBuilder().setNamePrefix("ExecutorServiceTest-").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * ExecutorService + Future实现
     */
    public void collectHomework1(){
        Future<String> future1 = executor.submit(() -> {
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(10);
            System.out.println(name + "小红交作业");
            return "小红的作业";
        });

        Future<String> future2 = executor.submit(() -> {
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(3);
            System.out.println(name +"小明交作业");
            return "小明的作业";
        });

        Future<String> future3 = executor.submit(() -> {
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(1);
            System.out.println(name +"小朋交作业");
            return "小朋的作业";
        });

        try {
            System.out.println("收" + future1.get());
            System.out.println("收" + future2.get());
            System.out.println("收" + future3.get());
            System.out.println("作业收完，班长将作业送到老师办公室");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    /**
     * CompletionService实现
     */
    public void collectHomework2() {
        CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
        completionService.submit(() -> {
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(10);
            System.out.println(name + "小红交作业");
            return "小红的作业";
        });

        completionService.submit(() -> {
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(3);
            System.out.println(name +"小明交作业");
            return "小明的作业";
        });

        completionService.submit(() -> {
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(1);
            System.out.println(name +"小朋交作业");
            return "小朋的作业";
        });

        try {
            System.out.println("收" + completionService.take().get());
            System.out.println("收" + completionService.take().get());
            System.out.println("收" + completionService.take().get());
            System.out.println("作业收完，班长将作业送到老师办公室");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }



        public static void main(String[] args) {
        CompletionServiceTest completionServiceTest = new CompletionServiceTest();
        completionServiceTest.collectHomework2();
    }
}
