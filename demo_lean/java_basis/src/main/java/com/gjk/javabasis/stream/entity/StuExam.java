package com.gjk.javabasis.stream.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2024-05-23 16:14
 **/
@Data
@AllArgsConstructor
public class StuExam {

    /**
     * 学生Id
     */
    private String stuId;

    /**
     * 科目
     */
    private String subject;

    /**
     * 得分
     */
    private BigDecimal score;

}
