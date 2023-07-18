package com.gjk.cache.local;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gjk.cache.common.Constant;
import com.gjk.cache.mapper.DemoMapper;
import com.gjk.cache.util.LocalCacheMonitor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * LocalCache
 *
 * @author: gaojiankang
 * @date: 2023/7/18 15:31
 * @description:
 */
@Slf4j
@Component
public class LocalCache {


    @Resource
    private DemoMapper demoMapper;

    /**
     * 本地缓存初始化
     */
    private final Cache<String, String> cache = CacheBuilder.newBuilder()
            // 基于容量回收。缓存的最大数量。
            .maximumSize(5000)
            // 过期失效回收
            // 没有被更新，失效时间(非自动失效，需有任意get put方法才会扫描过期失效数据)
            .expireAfterWrite(1L, TimeUnit.MINUTES)
            // 所有segment的初始总容量大小
            .initialCapacity(128)
            // 开启统计
            .recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return loadFromDb(key);
                }
            });
    /**
     * 本地缓存(带监控)初始化
     */
    private final Cache<String, String> cacheHasMonitor = LocalCacheMonitor.buildMonitorLocalCache("localCache_", CacheBuilder.newBuilder()
            // 基于容量回收。缓存的最大数量。
            .maximumSize(5000)
            // 过期失效回收
            // 没有被更新，失效时间(非自动失效，需有任意get put方法才会扫描过期失效数据)
            .expireAfterWrite(1L, TimeUnit.MINUTES)
            // 所有segment的初始总容量大小
            .initialCapacity(128)
            // 开启统计
            .recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return loadFromDb(key);
                }
            }));

    /**
     * 从db构建缓存
     *
     * @param key
     * @return
     */
    private String loadFromDb(String key) {
        String[] split = StringUtils.split(key, ":");
        return demoMapper.getInfo(split[1]);
    }

    /**
     * 从本地缓存获取信息
     */
    public String get(String id) {
        if (StringUtils.isBlank(id)) {
            id = "-1";
        }
        String cacheKey = String.format(Constant.LocalKey.DEMO_LOCAL_KEY, id);
        return cache.getIfPresent(cacheKey);
    }
}
