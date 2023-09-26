import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
public class Test {



    public static void main(String args[]) {
        int[] ints = new int[5];
        System.out.println(JSON.toJSONString(ints));

        int[] arr = {0,1,2,3,4};
        System.out.println(JSON.toJSONString(arr));
        arr[1] = 5;
        System.out.println(JSON.toJSONString(arr));


    }


}



