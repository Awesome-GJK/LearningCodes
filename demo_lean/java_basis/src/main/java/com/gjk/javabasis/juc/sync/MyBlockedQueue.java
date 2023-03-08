package com.gjk.javabasis.juc.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.poi.ss.formula.functions.T;

/**
 * MyBlockedQueue
 *
 * @author: gaojiankang
 * @date: 2022/11/10 11:23
 * @description:
 */
public class MyBlockedQueue {
    private final Object[] objects;

    private final Lock lock = new ReentrantLock();

    /**
     * 条件变量 队列不满
     */
    private final Condition notFull = lock.newCondition();

    /**
     * 条件变量 队列不空
     */
    private final Condition notEmpty = lock.newCondition();

    private int index = 0;

    public MyBlockedQueue(int num) {
        this.objects = new Object[num];
    }

    /**
     * 入队
     */
    public void enq(Object x){
        lock.lock();
        try {
            while(index > objects.length -1){
             //队列已满，进入notFull条件等待队列
                notFull.await();
            }
            //插入队列
            objects[index] = x;
            index++;
            //通知出队操作，唤醒notEmpty条件等待队列中的线程
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 出队
     */
    public Object deq(){
        Object object = null;
        lock.lock();
        try {
            while (index == 0 ){
                //队列为空,进入notEmpty条件等待队列
                notEmpty.await();
            }
            //抛出队列
            object = objects[index];
            objects[index] = null;
            index--;
            //通知入队操作,唤醒notFull条件等待队列中的线程
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return object;
    }

}
