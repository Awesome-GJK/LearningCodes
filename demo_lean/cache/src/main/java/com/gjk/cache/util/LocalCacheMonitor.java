package com.gjk.cache.util;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.util.concurrent.AtomicDouble;

import io.micrometer.core.instrument.Metrics;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * LocalCacheMonitor
 *
 * @author: gaojiankang
 * @date: 2023/7/18 16:39
 * @description:
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalCacheMonitor {

    /**
     * 获得受监控的本地缓存（监控一个传入的自定义的cache）, 配合Prometheus可实现本地缓存监控
     *
     * @param cacheName 缓存名称，注意唯一性
     * @param cache     本地缓存
     * @return
     */
    public static Cache<String, String> buildMonitorLocalCache(String cacheName, Cache<String, String> cache) {
        //运行监控
        runMonitor(cacheName, cache);
        return cache;
    }

    /**
     * 运行监控
     */
    private static void runMonitor(String cacheName, Cache cache) {
        //缓存命中数
        AtomicLong hitCount = new AtomicLong(0);
        //缓存命中率
        AtomicDouble hitRate = new AtomicDouble(0);
        //缓存未命中数
        AtomicLong missCount = new AtomicLong(0);
        //缓存未命中率
        AtomicDouble missRate = new AtomicDouble(0);
        //缓存大小
        AtomicLong size = new AtomicLong(0);
        //通过micrometer API完成统计
        Metrics.gauge(cacheName + "hit_count", hitCount);
        Metrics.gauge(cacheName + "hit_rate", hitRate);
        Metrics.gauge(cacheName + "miss_count", missCount);
        Metrics.gauge(cacheName + "miss_rate", missRate);
        Metrics.gauge(cacheName + "size", size);
        //每15秒采集一次数据
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            // 获取缓存统计信息
            CacheStats stats = cache.stats();
            // 更新统计信息
            hitCount.set(stats.hitCount());
            hitRate.set(stats.hitRate());
            missCount.set(stats.missCount());
            missRate.set(stats.missRate());
            size.set(cache.size());
        }, 0, 15, TimeUnit.SECONDS);
    }
}
