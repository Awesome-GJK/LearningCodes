package main.java;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
@Slf4j
public class Test {



    public static void main(String args[]) {

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

            try {
                System.out.println("runAsync:" + Thread.currentThread().getName());
                throw new RuntimeException("error");
            } catch (RuntimeException e) {
                System.out.println("发生异常，捕获:" + Thread.currentThread().getName());
            }
        }).whenComplete((v, t) -> {
            if (t != null) {
                System.out.println("error:" + Thread.currentThread().getName());
            } else {
                System.out.println("whenComplete:" + Thread.currentThread().getName());
            }
        });
        System.out.println("main");
    }


}



