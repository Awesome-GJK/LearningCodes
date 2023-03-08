package com.gjk.javabasis.juc.cas;

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
