package main.java;


import java.math.BigDecimal;

import com.jcraft.jsch.SftpException;

import cn.hutool.core.util.NumberUtil;
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

        BigDecimal bigDecimal1 = new BigDecimal("1.0");
        BigDecimal bigDecimal2 = new BigDecimal("2.1");
        BigDecimal bigDecimal3 = new BigDecimal("3.2");

        int num = NumberUtil.add(bigDecimal1, bigDecimal2, bigDecimal3).multiply(new BigDecimal(100)).intValue();

        System.out.println(String.valueOf(num));
    }


}



