package com.gjk.mybatis;

import java.util.concurrent.atomic.AtomicReference;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MybatisApplication
 *
 * @author: GJK
 * @date: 2022/6/15 0:04
 * @description:
 */
@SpringBootApplication
@MapperScan
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class);
    }



}
