package com.gjk.javabasis.stream;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.hutool.core.util.NumberUtil;

/**
 * @Classname GroupByList
 * @Description
 * @Date 2024/2/18 16:05
 * @Created by gaojiankang
 */
public class GroupByList {

    public static void main(String[] args) {

        groupListMaxCore();


    }

    /**
     * 对list进行分组，求出各组最大值
     */
    private static void groupListMaxCore() {
        Stu stu1 = new Stu();
        stu1.setClassName("1");
        stu1.setYwCore(90.05d);
        stu1.setSxCore(80.05d);

        Stu stu2 = new Stu();
        stu2.setClassName("1");
        stu2.setYwCore(95.05d);
        stu2.setSxCore(85.05d);

        Stu stu3 = new Stu();
        stu3.setClassName("2");
        stu3.setYwCore(85.05d);
        stu3.setSxCore(75.05d);

        Stu stu4 = new Stu();
        stu4.setClassName("2");
        stu4.setYwCore(80.05d);
        stu4.setSxCore(70.05d);
        List<Stu> school = Stream.of(stu1, stu2, stu3, stu4).collect(Collectors.toList());

        Map<String, Optional<BigDecimal>> classMaxCore = school.stream().collect(
                Collectors.groupingBy(Stu::getClassName,
                        Collectors.mapping(stu -> BigDecimal.valueOf(NumberUtil.add(stu.getYwCore(), stu.getSxCore())),
                                Collectors.maxBy(BigDecimal::compareTo))));
        classMaxCore.forEach((k, v) -> {
            System.out.println(k + "班最高分：" + v.get());
        });
    }


}
