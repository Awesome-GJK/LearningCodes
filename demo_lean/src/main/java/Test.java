package main.java;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
@Slf4j
public class Test {


    public static void main(String[] args) throws SftpException {
        List<String> list1 = Collections.emptyList();
        List<String> list2 = Collections.emptyList();
        List<String> list3 = Collections.emptyList();

        List<String> collect1 = Stream.of(list1, list2, list3).flatMap(Collection::stream).collect(Collectors.toList());

    }


}



