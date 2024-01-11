package innerlock;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    //创建1个map 代表共享数据
    final static HashMap<String, String> MAP=new HashMap<>();
    //创建一个印戳锁
    final static StampedLock STAMPED_LOCK=new StampedLock();
    /*
     * 对共享数据的写操作
     */
    public static Object put(String key,String value) {
        long stamp=STAMPED_LOCK.writeLock();
        try {
            System.out.println(getNowTime()+": 抢占了写锁，开始写操作");
            String put=MAP.put(key, value);
            return put;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println(getNowTime()+": 释放了写锁");
            STAMPED_LOCK.unlock(stamp);
        }
        return null;
    }
    /*
     * 对共享数据的悲观读操作
     */
    public static Object pessimisticRead(String key) {
        System.out.println(getNowTime()+":  进入过写模式，只能悲观读");
        long stamp=STAMPED_LOCK.readLock();
        try {
            System.out.println(getNowTime()+": 获取了读锁");
            String value=MAP.get(key);
            return value;
        } finally {
            System.out.println(getNowTime()+": 释放了读锁");
            STAMPED_LOCK.unlockRead(stamp);
        }
    }
    /*
     * 对共享数据的乐观读操作
     */
    public static Object optimisticRead(String key) {
        String value=null;
        long stamp=STAMPED_LOCK.tryOptimisticRead();
        if(stamp!=0) {
            System.out.println(getNowTime()+":  乐观锁的印戳值获取成功");
            value=MAP.get(key);
        }
        else if (stamp==0) { //代码1
            System.out.println(getNowTime()+":  乐观锁的印戳值获取失败，开始使用悲观读");
            return pessimisticRead(key);
        }
        if(!STAMPED_LOCK.validate(stamp)) {//代码2处
            System.out.println(getNowTime()+":  乐观读的印戳值已经过期");
            return pessimisticRead(key);
        }
        else {
            System.out.println(getNowTime()+":  乐观读的印戳值没有过期");
            return value;
        }
    }
    public static Date getNowTime() {
        return new Date();
    }
    public static void main(String[] args) throws InterruptedException {
        MAP.put("initKey", "initValue");
        Thread t1=new Thread(()->{
            System.out.println(optimisticRead("initKey"));
        },"读线程1");
        Thread t2=new Thread(()->{
            put("key1", "value1");
        },"写线程1");
        Thread t3=new Thread(()->{
            System.out.println(optimisticRead("initKey"));
        },"读线程2");
        t1.start();
        t1.join();
        t2.start();

        t3.start();
        Thread.sleep(1000);
    }

}

