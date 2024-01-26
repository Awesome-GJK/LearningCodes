package com.gjk.javabasis.enums.cacheEnum;

/**
 * @Classname EnumException
 * @Description TODO
 * @Date 2024/1/26 10:15
 * @Created by gaojiankang
 */
public class EnumException extends RuntimeException{

    private String code;

    public EnumException() {
        super();
    }

    public EnumException(String message) {
        super(message);
        this.code = "-1";
    }

    public EnumException(String message, String code) {
        super(message);
        this.code = code;
    }

    public EnumException(String message, Throwable cause) {
        super(message, cause);
        this.code = "-1";
    }

    public EnumException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public EnumException(Throwable cause) {
        super(cause);
        this.code = "-1";
    }

    public EnumException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public EnumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = "-1";
    }

    public EnumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
