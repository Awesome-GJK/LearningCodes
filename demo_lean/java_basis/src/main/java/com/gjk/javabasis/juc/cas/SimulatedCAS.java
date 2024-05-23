package com.gjk.javabasis.juc.cas;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SimulatedCAS
 *
 * @author: gaojiankang
 * @date: 2022/11/29 14:21
 * @description:
 */
public class SimulatedCAS {

    private volatile int count;

    void addOne(){
        while (!cas(count, count+1)){
        }
    }

    public static void main(String args[]) {
        List<String> collect = Stream.of("10", "200").collect(Collectors.toList());
        System.out.println(collect.stream().anyMatch(t -> "200".equals(t)));
        System.out.println(collect.stream().allMatch(t -> "200".equals(t)));

    }

    synchronized boolean cas(int expectValue, int newValue){
        //获取当前值
        int curValue = count;
        //期望值等于当前值，修改count，返回true
        if(curValue == expectValue){
            count = newValue;
            return true;
        }
        //否则返回false
        return false;
    }
}
