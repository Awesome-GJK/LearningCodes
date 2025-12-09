package com.gjk.javabasis.test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2025-08-12 10:23
 **/
public class SnowflakeIdUtil {

    /**
     * 获取雪花id
     */
    public static String nextIdStr() {
        Snowflake snowflake = IdUtil.getSnowflake(getWorkId());
        return snowflake.nextIdStr();
    }

    /**
     * workId使用IP生成
     *
     * @return workId
     */
    private static Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] intArr = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : intArr) {
                sums = sums + b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 失败就随机
            return RandomUtils.nextLong(0, 31);
        }
    }


    public static void main(String[] args) {

        long start = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            System.out.println(nextIdStr());
        }

        System.out.println((System.nanoTime() - start) / 1000000 + "ms");
    }
}
