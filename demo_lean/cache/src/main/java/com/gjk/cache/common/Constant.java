package com.gjk.cache.common;

/**
 * Constant
 *
 * @author: gaojiankang
 * @date: 2023/7/18 14:30
 * @description:
 */
public class Constant {
    public interface Common {
        String NO_DATA = "-999";
    }


    public interface RedisKey {
        String DEMO_REDIS_KEY = "DEMO_REDIS_KEY:%s";
    }

    public interface LocalKey {
        String DEMO_LOCAL_KEY = "DEMO_LOCAL_KEY:%s";
    }
}

