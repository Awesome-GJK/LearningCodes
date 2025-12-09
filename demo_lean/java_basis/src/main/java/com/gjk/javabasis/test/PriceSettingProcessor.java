package com.gjk.javabasis.test;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceSettingProcessor {

    public static List<PriceSetting> splitIntoHalfHourSlots(List<PriceSetting> priceSettings) {
        List<PriceSetting> result = new ArrayList<>(48);
        int index = 0;

        for (int i = 0; i < 48; i++) {

            // 开始时间计算
            int startHour = i / 2;
            int startMinute = (i % 2) * 30;
            String timeFrameStart = String.format("%02d:%02d", startHour, startMinute);

            // 结束时间计算（用i+1对应序号）
            int nextIndex = i + 1;
            int endHour = nextIndex / 2;
            int endMinute = (nextIndex % 2) * 30;
            String timeFrameEnd = String.format("%02d:%02d", endHour, endMinute);

            // 找到覆盖当前时段i的PriceSetting（spanStart <= i < spanEnd）
            while (index < priceSettings.size() - 1 && (i >= priceSettings.get(index).getSpanEnd() || i < priceSettings.get(index).getSpanStart())) {
                index++;
            }
            PriceSetting current = priceSettings.get(index);
            index = 0;

            result.add(PriceSetting.builder()
                    .spanStart(i)
                    .spanTimeStart(timeFrameStart)
                    .spanEnd(nextIndex % 48)  // 24:00对应0的编码
                    .spanTimeEnd(timeFrameEnd)
                    .electricityFee(current.getElectricityFee())
                    .serviceFee(current.getServiceFee())
                    .build());

        }
        return result;
    }

    // 假设PriceSetting类（需补充Getter方法）
    @Getter
    @Builder
    public static class PriceSetting {

        /**
         * 时段开始0-48
         */
        private int spanStart;


        /**
         * 时间段开始hh:mm
         */
        private String spanTimeStart;


        /**
         * 时段结束0-48
         */
        private int spanEnd;

        /**
         * 时间段结束hh:mm
         */


        private String spanTimeEnd;

        /**
         * ★时段电费
         */
        private BigDecimal electricityFee;


        /**
         * 时段服务费
         */
        private BigDecimal serviceFee;


        public static void main(String[] args) {
            // 示例数据初始化
            List<PriceSetting> priceSettings = new ArrayList<>();

            PriceSetting ps1 = PriceSetting.builder().spanStart(0).spanTimeStart("00:00").spanEnd(4).spanTimeEnd("02:00").electricityFee(BigDecimal.valueOf(2)).serviceFee(BigDecimal.valueOf(1)).build();
            PriceSetting ps2 = PriceSetting.builder().spanStart(4).spanTimeStart("02:00").spanEnd(10).spanTimeEnd("05:00").electricityFee(BigDecimal.valueOf(3)).serviceFee(BigDecimal.valueOf(1)).build();
            PriceSetting ps3 = PriceSetting.builder().spanStart(10).spanTimeStart("05:00").spanEnd(40).spanTimeEnd("20:00").electricityFee(BigDecimal.valueOf(4)).serviceFee(BigDecimal.valueOf(1)).build();
            PriceSetting ps4 = PriceSetting.builder().spanStart(40).spanTimeStart("20:00").spanEnd(48).spanTimeEnd("24:00").electricityFee(BigDecimal.valueOf(5)).serviceFee(BigDecimal.valueOf(1)).build();
            priceSettings.add(ps3);
            priceSettings.add(ps2);
            priceSettings.add(ps1);
            priceSettings.add(ps4);

            // 处理并打印结果
            List<PriceSetting> halfHourSlots = splitIntoHalfHourSlots(priceSettings);
            System.out.println(JSON.toJSONString(halfHourSlots));
        }
    }
}
