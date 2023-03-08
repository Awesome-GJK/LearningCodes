package com.gjk.javabasis.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch
 *
 * @author: GJK
 * @date: 2022/1/24 15:13
 * @description:
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        int N = 20;
        CountDownLatch doSignal = new CountDownLatch(N);
        ExecutorService executorService = new ThreadPoolExecutor(4,6,1, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        //创建任务，提交给线程池执行
        for(int i=0;i<N;i++){
            executorService.execute(new Worker(doSignal,i));
        }
        doSignal.await();
    }




    static class Worker implements Runnable{

        private final CountDownLatch doSignal;
        private final  int i;

        Worker(CountDownLatch doSignal, int i) {
            this.doSignal = doSignal;
            this.i = i;
        }

        public CountDownLatch getDoSignal() {
            return doSignal;
        }

        public int getI() {
            return i;
        }

        @Override
        public void run() {
            System.out.println("------------线程"+i+"开始执行run()方法！！！");
            doSignal.countDown();
        }
    }
}
