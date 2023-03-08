package com.gjk.spring.ioc_xml;

/**
 * MessageServiceImpl
 *
 * @author: GJK
 * @date: 2022/4/20 17:14
 * @description:
 */
public class MessageServiceImpl implements MessageService{
    @Override
    public String getMessage() {
        return "hello world";
    }
}
