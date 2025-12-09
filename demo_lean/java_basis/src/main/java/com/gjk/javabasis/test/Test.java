package com.gjk.javabasis.test;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2025-08-04 09:19
 **/
public class Test {

    public static void main(String[] args) {
        DateTime end = DateUtil.parse("2025-09-18 14:25:30");
        DateTime start = DateUtil.parse("2025-08-11 14:23:19");

        long between = DateUtil.between(end, DateUtil.date(), DateUnit.DAY);
        System.out.println(between);

    }
}
