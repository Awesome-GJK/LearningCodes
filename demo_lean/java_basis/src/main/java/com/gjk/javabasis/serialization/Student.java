package com.gjk.javabasis.serialization;

import java.io.Serializable;

/**
 * Student
 *
 * @author: GJK
 * @date: 2022/8/14 17:52
 * @description:
 */
public class Student implements Serializable {

    private int no;

    private String name;

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }



}
