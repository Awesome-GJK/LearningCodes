package com.gjk.javabasis.util;

import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.util.IdUtil;

/**
 * CalendarTest
 *
 * @author: gaojiankang
 * @date: 2022/9/15 15:18
 * @description:
 */
public class CalendarTest {

    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        System.out.println(instance.get(Calendar.SECOND));
        System.out.println(instance.get(Calendar.MINUTE));
        System.out.println(instance.get(Calendar.HOUR));
        System.out.println(instance.get(Calendar.DAY_OF_WEEK));
        System.out.println(instance.get(Calendar.DAY_OF_MONTH));
        System.out.println(instance.getActualMaximum(Calendar.DAY_OF_MONTH));

        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.fastUUID());


    }
}
