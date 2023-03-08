package com.gjk.designmode.zerenlian;

/**
 * ChainOfResponsibilityPattern
 *
 * @author: GJK
 * @date: 2022/4/2 18:00
 * @description:
 */
public class ChainOfResponsibilityPattern {

    public static void main(String[] args) {
        //组装责任链
        ConcreteHandler1 concreteHandler1 = new ConcreteHandler1();
        ConcreteHandler2 concreteHandler2 = new ConcreteHandler2();
        concreteHandler1.setNext(concreteHandler2);
        concreteHandler1.handleRequest(1);

    }
}
