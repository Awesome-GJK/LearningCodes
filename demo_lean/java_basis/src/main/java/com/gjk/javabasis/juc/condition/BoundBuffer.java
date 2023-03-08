package com.gjk.javabasis.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BoundBuffer
 *
 * @author: GJK
 * @date: 2021/12/21 11:33
 * @description:
 */
public class BoundBuffer {

    final Lock lock = new ReentrantLock();
    // Condition 依赖于lock产生的
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    //生产
    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                // 队列已满，等待，直到notfull才能继续生产
                notFull.await();
            }
            items[putptr] = obj;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            // 生产成功，队列已经not empty了，发个通知出去
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    //消费
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                // 队列为空，等到，直到队列not empty 才能继续消费
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            // 被消费掉一个，队列notfunll，发个通知出去
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
