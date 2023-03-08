package com.gjk.javabasis.juc.deadlock.loopWaiting;

/**
 * Account 账号
 *
 * @author: gaojiankang
 * @date: 2022/11/7 17:30
 * @description:
 */
public class Account {

    /**
     * 序号
     */
    private int id;

    /**
     * 余额
     */
    private Integer balance;

    void transfer(Account target, int amt) {
        //根据Id，控制获取资源先后顺序
        Account left = target;
        Account right = this;
        if(target.id > this.id){
            right = target;
            left = this;
        }
        //先锁序号小的
        synchronized (left){
            //再锁序号大的
            synchronized (right){
                if(balance > amt){
                    balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
