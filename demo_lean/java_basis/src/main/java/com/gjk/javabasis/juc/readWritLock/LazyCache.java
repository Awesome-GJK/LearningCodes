package com.gjk.javabasis.juc.readWritLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * LazyCache
 * 缓存数据懒加载方式
 *
 * @author: gaojiankang
 * @date: 2022/11/15 14:06
 * @description:
 */
public class LazyCache<K, V> {

    /**
     * 缓存，用于存放数据
     */
    private final Map<K, V> cache = new HashMap<>();

    /**
     * 初始化读写锁
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
     * 读缓存
     *
     * @param key
     * @return
     */
    public V get(K key, Function<K, V> function) {
        //读缓存
        readLock.lock();
        V v;
        try {
            v = cache.get(key);
        } finally {
            readLock.unlock();
        }

        //缓存存在数据，直接返回
        if (v != null) {
            return v;
        }

        //写缓存
        writeLock.lock();
        try {
            //再次判断缓存是否已经有值，在获取到写锁之前，有可能有其他线程写入缓存了
            v = cache.get(key);
            if (v == null) {
                //缓存不存在数据，通过回调获取
                v = function.apply(key);
                cache.put(key, v);
            }
        } finally {
            writeLock.unlock();
        }
        return v;
    }

}
