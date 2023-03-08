package com.gjk.designmode.moban.neww;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang3.RandomUtils;

/**
 * BankBusinessHandler2
 * 基于lambda表达式的模板方法设计模式实现方式2，对外提供具体业务方法，不提供模板方法
 *  *
 *
 * @author: GJK
 * @date: 2022/3/23 17:35
 * @description:
 */
public class BankBusinessHandler2 {


    private void execute(Supplier<String> supplier, Consumer<BigDecimal> consumer){
        String number = supplier.get();
        System.out.println(number);
        if (number.startsWith("vip")) {
            //Vip号分配到VIP柜台
            System.out.println("Assign To Vip Counter");
        }
        else if (number.startsWith("reservation")) {
            //预约号分配到专属客户经理
            System.out.println("Assign To Exclusive Customer Manager");
        }else{
            //默认分配到普通柜台
            System.out.println("Assign To Usual Manager");
        }
        consumer.accept(null);
        judge();

    }

    /**
     * 评价
     */
    private void judge() {
        System.out.println("give a praised");
    }

    /**
     * vip存钱
     * @param bigDecimal
     */
    public void saveVip(BigDecimal bigDecimal){
        execute(()->"vipNumber-00"+ RandomUtils.nextInt(),s-> System.out.println("save" + bigDecimal));
    }

    /**
     * 专属通道存钱
     * @param bigDecimal
     */
    public void saveReservation(BigDecimal bigDecimal){
        execute(()->"reservationNumber-00"+ RandomUtils.nextInt(),s-> System.out.println("save" + bigDecimal));
    }

    /**
     * 普通存钱
     * @param bigDecimal
     */
    public void save(BigDecimal bigDecimal){
        execute(()->"Number-00"+ RandomUtils.nextInt(),s-> System.out.println("save" + bigDecimal));
    }

}
