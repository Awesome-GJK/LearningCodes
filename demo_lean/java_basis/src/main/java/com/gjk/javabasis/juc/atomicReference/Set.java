package com.gjk.javabasis.juc.atomicReference;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;

/**
 * Set
 *
 * @author: gaojiankang
 * @date: 2022/11/21 17:06
 * @description:
 */
public class Set {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<List<String>> pos = new AtomicReference<>();
        AtomicReference<List<String>> dos = new AtomicReference<>();
        Thread thread1 = new Thread(() -> pos.set(Stream.of("1", "2", "3", "4").collect(Collectors.toList())));

        Thread thread2 = new Thread(() -> dos.set(Stream.of("1", "2", "3").collect(Collectors.toList())));
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        List<String> list1 = pos.get();
        List<String> list2 = dos.get();
        list1.removeAll(list2);

        System.out.println(JSON.toJSONString(list1));
    }
}
