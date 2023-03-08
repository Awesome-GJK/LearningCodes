package com.gjk.javabasis.map;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMapDemo
 *
 * @author: GJK
 * @date: 2022/7/5 23:02
 * @description:
 */
public class HashMapDemo {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<999999999; i++){
            map.put(i,i+1);
        }
    }
}
