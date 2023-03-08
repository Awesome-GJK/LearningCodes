package com.gjk.designmode.zerenlian;


import lombok.Setter;

/**
 * Handler
 *
 * 责任链模式，抽象父类
 *
 * @author: GJK
 * @date: 2022/4/2 17:40
 * @description:
 */
@Setter
public abstract class Handler {

    protected Handler next;

    protected int number = 1;

    public abstract void handleRequest(int request);

}
