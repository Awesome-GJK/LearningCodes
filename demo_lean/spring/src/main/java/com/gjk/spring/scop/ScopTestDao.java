package com.gjk.spring.scop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * TestDao
 *
 * @author: GJK
 * @date: 2022/4/22 14:16
 * @description:
 */
@Repository
@Scope(value = "prototype")
public class ScopTestDao {


}
