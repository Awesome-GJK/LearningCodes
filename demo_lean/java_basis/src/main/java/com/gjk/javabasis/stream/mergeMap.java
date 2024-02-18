package com.gjk.javabasis.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

/**
 * @Classname mergeMap
 * @Description
 * @Date 2024/2/18 14:28
 * @Created by gaojiankang
 */
public class mergeMap {


    public static void main(String[] args) {

        mergeMapAndList();
    }

    private static void mergeMapAndList() {
        Map<String, List<String>> map1 = new HashMap<>();
        map1.put("key1", Lists.newArrayList("value1", "value2"));
        map1.put("key2", Lists.newArrayList("value3"));

        Map<String, List<String>> map2 = new HashMap<>();
        map2.put("key2", Lists.newArrayList("value4")); // 相同的键
        map2.put("key3", Lists.newArrayList("value5"));

        // 使用 Stream API 合并两个 Map
        Map<String, List<String>> mergedMap = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (list1, list2) -> {
                            List<String> combinedList = new ArrayList<>(list1);
                            combinedList.addAll(list2);
                            return combinedList;
                        }
                ));

        System.out.println(mergedMap);
    }
}
