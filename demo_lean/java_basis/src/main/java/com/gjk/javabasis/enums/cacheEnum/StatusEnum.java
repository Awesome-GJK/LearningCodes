package com.gjk.javabasis.enums.cacheEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Classname StatusEnum
 * @Description TODO
 * @Date 2024/1/26 10:09
 * @Created by gaojiankang
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusEnum {

    WAIT_INVOICED("0", "待开票"),
    INVOICED("1", "已开票"),
    DISMISSED("2", "已驳回")
    ;

    private final String code;
    private final String desc;

    static {
        //以枚举code构建缓存
        EnumCache.registerByValue(StatusEnum.class, StatusEnum.values(), StatusEnum::getCode);

        //以枚举desc构建缓存
        EnumCache.registerByName(StatusEnum.class, StatusEnum.values());
    }
}
