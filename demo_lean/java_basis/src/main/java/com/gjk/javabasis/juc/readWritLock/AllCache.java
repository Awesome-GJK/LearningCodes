package com.gjk.javabasis.juc.readWritLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * AllCache
 *
 * @author: gaojiankang
 * @date: 2022/11/15 14:46
 * @description:
 */
public class AllCache <K,V>{

    /**
     * 缓存
     */
    private final Map<K,V> cache = new HashMap<>();

    /**
     * 新建读写锁
     */
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    /**
     * 写锁
     */
    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();



    /**
     * 从缓存获取数据
     * @param k
     * @return
     */
    public V get(K k){
        readLock.lock();
        try {
            return cache.get(k);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 数据写入缓存
     * @param k
     * @param v
     */
    public void put(K k, V v){
        writeLock.lock();
        try {
            cache.put(k,v);
        } finally {
            writeLock.unlock();
        }
    }

}
