package com.gjk.designmode.moban.neww;

import java.math.BigDecimal;
import java.util.function.Consumer;

import org.apache.commons.lang3.RandomUtils;

/**
 * BankBusinessHandler
 * 基于lambda表达式的模板方法设计模式实现方式1，对外提供具体业务方法，不提供模板方法
 *
 * @author: GJK
 * @date: 2022/3/23 16:57
 * @description:
 */
public class BankBusinessHandler {

    /**
     * 模板方法
     */
    private void execute(Consumer<BigDecimal> consumer){
        getNumber();
        consumer.accept(null);
        judge();
    }

    /**
     * 取号
     */
    private void getNumber() {
        System.out.println("number-00" + RandomUtils.nextInt());
    }

    /**
     * 评价
     */
    private void judge() {
        System.out.println("give a praised");
    }

    /**
     * 存钱
     * @param bigDecimal
     */
    public void save(BigDecimal bigDecimal){
         execute(s-> System.out.println("save" + bigDecimal));
    }

    /**
     * 取钱
     * @param amount
     */
    public void draw(BigDecimal amount) {
        execute(a -> System.out.println("draw " + amount));
    }

    /**
     * 理财
     * @param amount
     */
    public void moneyManage(BigDecimal amount) {
        execute(a -> System.out.println("draw " + amount));
    }
}
