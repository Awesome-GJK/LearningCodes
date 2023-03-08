package com.gjk.javabasis.juc.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * BoilWaterForTea
 *
 * @author: gaojiankang
 * @date: 2022/11/29 17:11
 * @description:
 */
public class BoilWaterForTea {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> t2 = new FutureTask<>(new SecondPartTask());
        FutureTask<String> t1 = new FutureTask<>(new FirstPartTask(t2));
        Thread thread2 = new Thread(t2);
        Thread thread1 = new Thread(t1);
        thread2.start();
        thread1.start();
        System.out.println(t1.get());

    }





    private static class FirstPartTask implements Callable<String>{
        FutureTask<String> t2;

        public FirstPartTask(FutureTask<String> t2) {
            this.t2 = t2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1:洗水壶。。。。。。");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1:烧开水。。。。。。");
            TimeUnit.SECONDS.sleep(15);
            String s = t2.get();
            System.out.println("T1:泡茶。。。。。。");

            return "上茶" + s;
        }
    }

    private static class SecondPartTask implements Callable<String>{
        @Override
        public String call() throws Exception {
            System.out.println("T2:洗茶壶。。。。。。");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2:洗茶杯。。。。。。");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2:拿茶叶。。。。。。");
            TimeUnit.SECONDS.sleep(1);

            return "铁观音";
        }
    }

}
