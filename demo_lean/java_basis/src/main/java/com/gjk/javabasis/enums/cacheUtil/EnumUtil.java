package com.gjk.javabasis.enums.cacheUtil;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Classname EnumUtil
 * @Description 枚举工具类
 * @Date 2024/1/26 10:25
 * @Created by gaojiankang
 */
public class EnumUtil {

    public  static <E extends Enum<?> & BaseEnum> Optional<E> codeOf(Class<E> enumClass, String code) {

        return Arrays.stream(enumClass.getEnumConstants()).filter(e -> e.getCode().equals(code)).findAny();
    }
}
