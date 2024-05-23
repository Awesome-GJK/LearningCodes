package com.gjk.javabasis.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gjk.javabasis.stream.entity.Stu;
import com.gjk.javabasis.stream.entity.StuExam;

import cn.hutool.core.util.NumberUtil;

/**
 * @Classname GroupingBy
 * @Description
 * @Date 2024/2/18 16:05
 * @Created by gaojiankang
 */
public class GroupingBy {

    public static void main(String[] args) {



    }

    /**
     * 对stuList进行分组，求出各班级语数总分最高分
     */
    private static void classYwSxSumMaxCore() {
        Stu stu1 = new Stu();
        stu1.setClassName("1");
        stu1.setId("a");
        stu1.setYwCore(90.05d);
        stu1.setSxCore(80.05d);

        Stu stu2 = new Stu();
        stu2.setClassName("1");
        stu1.setId("b");
        stu2.setYwCore(95.05d);
        stu2.setSxCore(85.05d);

        Stu stu3 = new Stu();
        stu3.setClassName("2");
        stu1.setId("c");
        stu3.setYwCore(85.05d);
        stu3.setSxCore(75.05d);

        Stu stu4 = new Stu();
        stu4.setClassName("2");
        stu1.setId("d");
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

    /**
     * 对stuList进行分组，求出各班级英语总分
     */
    private static void classYySumCore() {
        Stu stu1 = new Stu();
        stu1.setClassName("1");
        stu1.setId("a");
        stu1.setYyCore(new BigDecimal(90));

        Stu stu2 = new Stu();
        stu2.setClassName("1");
        stu1.setId("b");
        stu2.setYyCore(new BigDecimal(80));

        Stu stu3 = new Stu();
        stu3.setClassName("2");
        stu1.setId("c");
        stu3.setYyCore(new BigDecimal(90));

        Stu stu4 = new Stu();
        stu4.setClassName("2");
        stu1.setId("d");
        stu4.setYyCore(new BigDecimal(80));
        List<Stu> school = Stream.of(stu1, stu2, stu3, stu4).collect(Collectors.toList());

        // reducing(BigDecimal.ZERO, BigDecimal::add) 这个BigDecimal.ZERO代表初始值，将元素值和初始值相加
        Map<String, BigDecimal> classTotalCore = school.stream().collect(
                Collectors.groupingBy(Stu::getClassName, Collectors.mapping(Stu::getYyCore, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    /**
     * 对StuExamList进行分组，求出各学生各科目多次考试分数总和
     */
    private static void test() {
        List<StuExam> dataList = Arrays.asList(
                new StuExam("1", "a", new BigDecimal("10")),
                new StuExam("1", "a", new BigDecimal("5")),
                new StuExam("1", "b", new BigDecimal("2")),
                new StuExam("1", "b", new BigDecimal("4")),
                new StuExam("2", "a", new BigDecimal("3")),
                new StuExam("2", "a", new BigDecimal("6")),
                new StuExam("2", "b", new BigDecimal("4")),
                new StuExam("2", "b", new BigDecimal("8"))
        );

        Map<String, Map<String, BigDecimal>> sumMap = dataList.stream()
                .collect(Collectors.groupingBy(StuExam::getStuId,
                        Collectors.groupingBy(StuExam::getSubject,
                                Collectors.mapping(StuExam::getScore, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))));

        // 打印结果
        sumMap.forEach((id, typeMap) -> {
            System.out.println("ID: " + id);
            typeMap.forEach((type, sum) -> {
                System.out.println("  Type: " + type + ", Sum: " + sum);
            });
        });
    }


}
