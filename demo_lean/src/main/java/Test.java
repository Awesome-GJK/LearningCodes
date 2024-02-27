package main.java;

import cn.hutool.core.text.CharSequenceUtil;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
public class Test {



    public static void main(String args[]) {
        String str1 = "132456789978 ";
        String str2 = "wqeqeqeqeqwe ";
        String str3 = "13WW4567qw123 ";

        System.out.println(CharSequenceUtil.isNumeric(str1));
        System.out.println(CharSequenceUtil.isAllCharMatch(str2, Character::isLetter));
        System.out.println(CharSequenceUtil.isAllCharMatch(str3, Character::isLetterOrDigit));
        System.out.println(str1.length());
        System.out.println(str2.length());

    }


}



