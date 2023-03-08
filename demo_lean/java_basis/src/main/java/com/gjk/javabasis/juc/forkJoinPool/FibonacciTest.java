package com.gjk.javabasis.juc.forkJoinPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * FibonacciTest
 *
 * @author: gaojiankang
 * @date: 2023/1/6 9:59
 * @description:
 */
public class FibonacciTest {


    public static void main(String[] args) {
        // 创建分治任务线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        // 创建分治任务
        Fibonacci fib = new Fibonacci(2);
        // 启动分治任务
        Integer result = fjp.invoke(fib);
        // 输出结果
        System.out.println(result);
    }

    static class Fibonacci extends RecursiveTask<Integer>{

        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if(n <=1){
                return n;
            }
            Fibonacci fibonacci1 = new Fibonacci(n - 1);
            //创建子任务
            fibonacci1.fork();
            Fibonacci fibonacci2 = new Fibonacci(n - 2);
            //等待子任务结果，合并结果
            return fibonacci2.compute() + fibonacci1.join();

        }
    }
}
