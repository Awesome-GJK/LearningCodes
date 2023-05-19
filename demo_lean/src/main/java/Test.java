import com.alibaba.fastjson.JSONObject;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
public class Test {



    public static void main(String args[]) {
        String str = "{\"data\":\"SzFW5qOZ2MViICUWCzqfEieWMA8t3hcBLHMg73iI36FHWJUjG2OQeN4kzDwX2bn7AFBs/qkXceBR7whcQsGUvl7PXKKB8uqpR9bQ1hoRpw4=\",\"resultCode\":\"0\",\"resultDesc\":\"请求成功\"}";

        JSONObject notifyResult = JSONObject.parseObject(str);

    }


}



