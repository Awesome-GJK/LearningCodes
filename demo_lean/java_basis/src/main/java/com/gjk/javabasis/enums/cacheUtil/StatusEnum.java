package com.gjk.javabasis.enums.cacheUtil;

/**
 * @Classname StatusEnum
 * @Description TODO
 * @Date 2024/1/26 10:39
 * @Created by gaojiankang
 */
public enum StatusEnum implements BaseEnum{

    /**
     * 未支付
     */
    WAITING_TRANSFER("1", "未支付"),
    /**
     * 未支付
     */
    HAVE_TRANSFERRED("2", "已支付"),
    ;

    private final String code;

    private final String param;

    StatusEnum(String code, String param) {
        this.code = code;
        this.param = param;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return param;
    }
}
