package com.gjk.javabasis.juc.sync;

import cn.hutool.core.date.DateUtil;

/**
 * Test
 *
 * @author: gaojiankang
 * @date: 2022/11/8 16:51
 * @description:
 */
public class Test {

    private long num;

    public synchronized long getNum() {
        return num;
    }

    public synchronized void setNum(long num) {
        this.num = num;
    }

    void add10K(){
        int idx = 0;
        while (idx< 10000){
            setNum(getNum()+1);
            idx++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread1-" + Thread.currentThread().getName() + "startTime:" + DateUtil.date().toMsStr());
                test.add10K();
                System.out.println("Thread1-" + Thread.currentThread().getName() + "endTime:" + DateUtil.date().toMsStr());
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread2-" + Thread.currentThread().getName() + "startTime:" + DateUtil.date().toMsStr());
                test.add10K();
                System.out.println("Thread2-" + Thread.currentThread().getName() + "endTime:" + DateUtil.date().toMsStr());
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(test.num);
    }
}
