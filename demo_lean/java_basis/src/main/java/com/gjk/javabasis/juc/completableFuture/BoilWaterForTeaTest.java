package com.gjk.javabasis.juc.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * BoilWaterForTeaTest
 *
 * @author: gaojiankang
 * @date: 2022/12/23 11:24
 * @description:
 */
public class BoilWaterForTeaTest {

    public static void main(String[] args) {

        CompletableFuture<Void> t1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1:洗水壶。。。。。。");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T1:烧开水。。。。。。");
            sleep(15, TimeUnit.SECONDS);
        });

        CompletableFuture<String> t2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2:洗茶壶。。。。。。");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T2:洗茶杯。。。。。。");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2:拿茶叶。。。。。。");
            sleep(1, TimeUnit.SECONDS);
            return "龙井";
        });

        CompletableFuture<String> t3 = t1.thenCombine(t2, (k, v) -> {
            System.out.println("T3:泡茶。。。。。。");
            return "上茶" + v;
        });
        System.out.println(t3.join());
    }

    public static void sleep(long timeout, TimeUnit u) {
        try {
            u.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
