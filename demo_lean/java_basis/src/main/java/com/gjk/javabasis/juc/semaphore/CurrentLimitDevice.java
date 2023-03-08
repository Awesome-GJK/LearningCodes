package com.gjk.javabasis.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * CurrentLimitDevice
 *
 * @author: gaojiankang
 * @date: 2022/11/14 19:53
 * @description:
 */
public class CurrentLimitDevice<T>{

    private final Semaphore semaphore;


    public CurrentLimitDevice(int size) {
        this.semaphore = new Semaphore(size);
    }

    public void run(Consumer<T> consumer, T param) throws InterruptedException {
        semaphore.acquire();
        try {
            consumer.accept(param);
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        CurrentLimitDevice<String> currentLimitDevice2 = new CurrentLimitDevice<String>(10);
        try {
            currentLimitDevice2.run(System.out::println, "GJK");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
