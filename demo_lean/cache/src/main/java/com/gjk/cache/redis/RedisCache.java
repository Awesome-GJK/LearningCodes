package com.gjk.cache.redis;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gjk.cache.common.Constant;
import com.gjk.cache.mapper.DemoMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisCache
 *
 * @author: gaojiankang
 * @date: 2023/7/18 14:24
 * @description:
 */
@Slf4j
@Component
public class RedisCache {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private DemoMapper demoMapper;

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    public String get(String id) {
        // 从缓存中获取
        String redisKey = String.format(Constant.RedisKey.DEMO_REDIS_KEY, id);
        String info = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.equals(info, Constant.Common.NO_DATA)) {
            // 命中穿透值
            return null;
        } else if (StringUtils.isNotEmpty(info)) {
            // 命中正常值
            return JSON.parseObject(info, String.class);
        } else {
            // 重建缓存
            return rebuild(id, redisKey);
        }
    }

    /**
     * 重建信息缓存
     *
     * @param id
     * @param redisKey
     * @return
     */
    private String rebuild(String id, String redisKey) {
        String redisValue;

        // 查信息
        String info = demoMapper.getInfo(id);
        if (Objects.isNull(info)) {
            redisValue = Constant.Common.NO_DATA;
            redisTemplate.opsForValue().set(redisKey, redisValue, 10, TimeUnit.MINUTES);
        } else {
            redisValue = JSON.toJSONString(info);
            redisTemplate.opsForValue().set(redisKey, redisValue, 6, TimeUnit.HOURS);
        }
        return info;
    }

    /**
     * 删除缓存
     *
     * @param id
     * @return
     */
    public Boolean del(String id) {
        String redisKey = String.format(Constant.RedisKey.DEMO_REDIS_KEY, id);
        return redisTemplate.delete(redisKey);
    }

    /**
     * 批量删除活动缓存
     *
     * @param ids
     * @return
     */
    public Long mulDel(List<String> ids) {
        List<String> redisKeys = ids.stream()
                .map(activityId -> String.format(Constant.RedisKey.DEMO_REDIS_KEY, activityId))
                .collect(Collectors.toList());
        return redisTemplate.delete(redisKeys);
    }


}
