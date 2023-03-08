package com.gjk.designmode.moban;

import java.math.BigDecimal;

import com.gjk.designmode.moban.neww.BankBusinessHandler2;
import com.gjk.designmode.moban.old.AbstractBusinessHandler;


/**
 * action
 *
 * @author: GJK
 * @date: 2022/3/23 16:51
 * @description:
 */
public class action {


    public static void main1(String[] args) {
        AbstractBusinessHandler.SaveMoneyHandler saveMoneyHandler = new AbstractBusinessHandler.SaveMoneyHandler();
        saveMoneyHandler.execute();
    }


    public static void main(String[] args) {
        BankBusinessHandler2 bankBusinessHandler = new BankBusinessHandler2();
        bankBusinessHandler.saveVip(new BigDecimal(1000));
    }
}
