package com.gjk.javabasis.juc.sync;

/**
 * InterruptedTest
 *
 * @author: gaojiankang
 * @date: 2022/11/10 15:30
 * @description:
 */
public class InterruptedTest {


    public static void main(String[] args) {

        /**
         * 中断被try-catch捕获，重置中断标志位，所有isInterrupted永远是false,所以一直在while里面转
         *
         * 解决方案：将while循环也放到try-catch中，或者在try-catch中调用当前线程的Interrupted()方法
         */
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("进入isInterrupted()，即将break");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("try-catch捕获InterruptedException");
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        while (true){
            new Thread(() -> {
                System.out.println("调用 thread1.interrupt()");
                thread1.interrupt();
            }).start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
