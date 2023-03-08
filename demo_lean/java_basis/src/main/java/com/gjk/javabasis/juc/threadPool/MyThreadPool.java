package com.gjk.javabasis.juc.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import lombok.SneakyThrows;


/**
 * MyThreadPool
 *
 * @author: gaojiankang
 * @date: 2022/11/29 16:02
 * @description:
 */
public class MyThreadPool {

    /**
     * 阻塞队列
     */
    private BlockingQueue<Runnable> workQueue;

    /**
     * 线程池大小
     */
    private List<WorkThread> threads;

    public MyThreadPool(BlockingQueue<Runnable> workQueue, int size) {
        this.workQueue = workQueue;
        this.threads = new ArrayList<>(size);
        for (int i=0; i<size;i++){
            WorkThread workThread = new WorkThread("WorkThread" + i);
            workThread.run();
            this.threads.add(workThread);
        }
    }


    /**
     * 提交任务的方法
     * @param runnable
     * @throws InterruptedException
     */
    public void execute(Runnable runnable) throws InterruptedException {
        this.workQueue.put(runnable);
    }


    /**
     * 工作线程内部类
     */
    private class WorkThread extends Thread {

        public WorkThread(String name) {
            super(name);
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true){
                Runnable task = workQueue.take();
                task.run();
            }
        }
    }
}
