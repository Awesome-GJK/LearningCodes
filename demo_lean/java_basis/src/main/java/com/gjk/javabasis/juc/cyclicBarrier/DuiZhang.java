package com.gjk.javabasis.juc.cyclicBarrier;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * DuiZhang
 *
 * @author: gaojiankang
 * @date: 2022/11/28 14:12
 * @description:
 */
public class DuiZhang {

    private final Vector<AtomicReference<List<String>>> posVector = new Vector<>();

    private final Vector<AtomicReference<List<String>>> dosVector = new Vector<>();

    public final ExecutorService executor = new ThreadPoolExecutor(
            2,
            4,
            1L,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(16),
            new ThreadFactoryBuilder().setNameFormat("duiZhang-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, this::check);


    public void main(String[] args) {
        DuiZhang duiZhang = new DuiZhang();
        AtomicReference<List<String>> pos = new AtomicReference<>();
        AtomicReference<List<String>> dos = new AtomicReference<>();
        duiZhang.executor.execute(() -> {
            pos.set(Stream.of("1", "2", "3", "4").collect(Collectors.toList()));
            posVector.add(pos);
            try {
                duiZhang.cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        duiZhang.executor.execute(() -> {
            dos.set(Stream.of("1", "2", "3").collect(Collectors.toList()));
            dosVector.add(dos);
            try {
                duiZhang.cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void check() {
        AtomicReference<List<String>> pos = posVector.remove(0);
        AtomicReference<List<String>> dos = dosVector.remove(0);
        List<String> list1 = pos.get();
        List<String> list2 = dos.get();
        list1.removeAll(list2);

        System.out.println(JSON.toJSONString(list1));
    }
}
