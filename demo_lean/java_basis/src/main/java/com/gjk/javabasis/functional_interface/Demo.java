package com.gjk.javabasis.functional_interface;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Demo
 *
 * @author: gaojiankang
 * @date: 2022/9/22 11:53
 * @description:
 */
public class Demo {

    public static void main(String[] args) {
        Stu stu = new Stu();
        stu.setName("q,2,+");
        translatePriceType(stu, Stu::getName, stu::setName);
        System.out.println(stu.getName());
    }



    public static  <T> void translatePriceType(T data, Function<T,String> getFunction, Consumer<String> setConsumer){
        if(data == null || StringUtils.isBlank(getFunction.apply(data))){
            return;
        }
        String[] codes = getFunction.apply(data).split(",");
        String typeDesc = Arrays.stream(codes).collect(Collectors.joining("!"));
        setConsumer.accept(typeDesc);
    }
}
