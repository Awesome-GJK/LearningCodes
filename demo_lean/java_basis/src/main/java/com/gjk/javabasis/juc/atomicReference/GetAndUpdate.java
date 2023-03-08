package com.gjk.javabasis.juc.atomicReference;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.collection.CollectionUtil;

/**
 * GetAndUpdate
 *
 * @author: gaojiankang
 * @date: 2022/11/21 17:05
 * @description:
 */
public class GetAndUpdate {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<List<String>> reference1 = new AtomicReference<>();
        Thread thread1 = new Thread(() -> {
            if (CollectionUtil.isEmpty(reference1.get())) {
                reference1.set(Stream.of("1", "2", "3", "4").collect(Collectors.toList()));
            } else {
                reference1.getAndUpdate(e -> {
                    e.add("4");
                    return e;
                });
            }
        });

        Thread thread2 = new Thread(() -> {
            if (CollectionUtil.isEmpty(reference1.get())) {
                reference1.set(Stream.of("1", "2", "3").collect(Collectors.toList()));
            } else {
                reference1.getAndUpdate(e -> {
                    e.add("4");
                    return e;
                });
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        List<String> list1 = reference1.get();

        System.out.println(JSON.toJSONString(list1));
    }

}
