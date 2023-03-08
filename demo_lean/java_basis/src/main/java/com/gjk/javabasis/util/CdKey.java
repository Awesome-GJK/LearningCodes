package com.gjk.javabasis.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * CDKey
 *
 * @author: gaojiankang
 * @date: 2022/10/9 10:35
 * @description:
 */
public class CdKey {

    public static void main(String[] args) {
        List<String> randomCode = createRandomCode(12, 100);
        System.out.println(randomCode.size());
    }

    public static String createRandomCode(int num) {
        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 58 + 65);
            if (intVal >= 91 && intVal <= 96) {
                i--;
            }
            if (intVal < 91 || intVal > 96) {
                if (intVal % 2 == 0) {
                    str += (char) intVal;
                } else {
                    str += (int) (Math.random() * 10);
                }
            }
        }
        return str;
    }

    /**
     * 生成total数量的num位的随机字符串(数字、大写字母随机混排)
     * @param num
     * @param total
     * @return
     */
    public static List<String> createRandomCode(int num, int total){
        Set<String> set = new HashSet<>(total);
        for (int i=0;i<total;i++){
            set.add(createRandomCode(num));
            if (set.size() == i){
                i--;
            }
        }
        return new ArrayList<>(set);
    }
}
