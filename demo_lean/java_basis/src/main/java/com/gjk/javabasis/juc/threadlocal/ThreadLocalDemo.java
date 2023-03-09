package com.gjk.javabasis.juc.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * ThreadLocalDemo
 *
 * @author: gaojiankang
 * @date: 2023/3/9 15:35
 * @description:
 */
public class ThreadLocalDemo {

    private ThreadLocal<Integer> intThreadLocal = ThreadLocal.withInitial(() -> 0);

    private ThreadLocal<String> strThreadLocal = ThreadLocal.withInitial(() -> "Hello");

    private final Semaphore semaphore = new Semaphore(1);


    private static final ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            5L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            new ThreadFactoryBuilder().setNameFormat("ThreadLocal-test-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public class RunnableDemo implements Runnable {
        private String str;

        private Integer num;

        public RunnableDemo(String str, Integer num) {
            this.str = str;
            this.num = num;
        }

        @Override
        public void run() {
            try {
//                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "intThreadLocal:" + intThreadLocal.get());
                System.out.println(Thread.currentThread().getName() + "strThreadLocal:" + strThreadLocal.get());

                intThreadLocal.set(num);
                strThreadLocal.set(strThreadLocal.get() + " " + str);

                System.out.println(Thread.currentThread().getName() + "intThreadLocal:" + intThreadLocal.get());
                System.out.println(Thread.currentThread().getName() + "strThreadLocal:" + strThreadLocal.get());

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
//                semaphore.release();
            }
        }
    }

    public void execute() {
        RunnableDemo demo1 = new RunnableDemo("runnableDemo1", 100);
        RunnableDemo demo2 = new RunnableDemo("runnableDemo2", 200);
        RunnableDemo demo3 = new RunnableDemo("runnableDemo3", 300);
        executor.execute(demo1);
        executor.execute(demo2);
        executor.execute(demo3);
    }

    public static void main(String[] args) {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        threadLocalDemo.execute();
    }


}
