package main.java;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
public class Test {



    public static void main(String args[]) {
        List<String> collect = Stream.of("10", "200").collect(Collectors.toList());
        System.out.println(collect.stream().anyMatch(t -> "200".equals(t)));
        System.out.println(collect.stream().allMatch(t -> "200".equals(t)));

    }


}



