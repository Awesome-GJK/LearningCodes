package com.gjk.javabasis.juc.readWritLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * LockUpOrDown
 * 读写锁的升级和降级
 *
 * @author: gaojiankang
 * @date: 2022/11/15 15:47
 * @description:
 */
public class LockUpOrDown<K, V> {

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
     * 锁升级，读写锁不支持
     *
     * @param key
     * @return
     */
    public void get(K key, Function<K, V> function, Consumer<V> consumer) {
        //读加锁
        readLock.lock();
        V v;
        try {
            v = cache.get(key);
            //缓存不存在数据
            if (v == null) {
                readLock.unlock();
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
                    //锁降级，获取读锁
                    readLock.lock();
                } finally {
                    writeLock.unlock();
                }
            }
            consumer.accept(v);
        } finally {
            readLock.unlock();
        }
    }

}
