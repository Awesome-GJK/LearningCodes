package com.gjk.spring.scop;


import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

/**
 * TestService
 *
 * @author: GJK
 * @date: 2022/4/22 14:15
 * @description:
 */

@Service
public abstract class ScopTestService {



      //方式一
//    @Lookup
//    public TestDao getTestDao() {
//        return null;
//    }

    //方式二 需要将类改为抽象类
    @Lookup
    public abstract ScopTestDao getTestDao();

    public void sysout(){
        System.out.println("TestService： " + this.hashCode());
        System.out.println("TestDao： " + getTestDao().hashCode());
    }



}
