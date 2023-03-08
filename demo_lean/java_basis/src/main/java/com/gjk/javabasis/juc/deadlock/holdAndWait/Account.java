package com.gjk.javabasis.juc.deadlock.holdAndWait;

/**
 * Account 账户
 *
 * @author: gaojiankang
 * @date: 2022/11/7 16:42
 * @description:
 */
public class Account {


    /**
     * 转入转出资源分配器
     *  allocator 为单列
     */
    private Allocator<Account> actr;

    /**
     * 账户余额
     */
    private Integer balance;

    public Account() {
        this.actr = Allocator.getInstance();
    }

    /**
     * 转账
     * @param target
     * @param amt
     */
    void transfer(Account target, int amt){
        // 自选申请转出账户和转入账户，直到成功
        try {
            actr.apply(this,target);
            //锁住转出账户
            synchronized (this){
                //锁定转入账户
                synchronized (target){
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放转出账号和转入账号
            actr.free(this, target);
        }
    }
}
