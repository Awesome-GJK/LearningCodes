package com.gjk.cache.mapper;

import org.springframework.stereotype.Repository;

/**
 * DemoMapper
 *
 * @author: gaojiankang
 * @date: 2023/7/18 14:27
 * @description:
 */
@Repository
public class DemoMapper {

    /**
     * 模拟从db中查询出的数据
     *
     * @return
     */
    public String getInfo(String id) {
        return "info" + id;
    }
}
