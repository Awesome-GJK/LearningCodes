package main.java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
@Slf4j
public class Test {


    public static void main(String[] args) {
        String str = "\"{\\\"content\\\":\\\"{\\\\\\\"notifyType\\\\\\\":1,\\\\\\\"paymentStatus\\\\\\\":1,\\\\\\\"terminalId\\\\\\\":\\\\\\\"1827898690985701378\\\\\\\",\\\\\\\"userId\\\\\\\":\\\\\\\"1828259733142470657\\\\\\\"}\\\",\\\"id\\\":\\\"1827898690985701378\\\",\\\"topicClass\\\":\\\"com.qcln.charge.server.application.redis.handler.StartChargeResultHandler\\\"}\"";

        JSONObject jsonObject = JSON.parseObject(str);


    }


}



