package main.java.price;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2025-01-24 15:50
 **/
public class PriceTemplate {

    public static void main(String[] args) {
        // 初始化测试数据
        List<PriceType> priceTypes = Arrays.asList(
                new PriceType(1, "尖", 1.0),
                new PriceType(2, "峰", 1.0),
                new PriceType(3, "平", 1.0),
                new PriceType(4, "谷", 1.0)
        );

        List<PriceTime> priceTimes = Arrays.asList(
                new PriceTime(0, 2, "00:00", "01:00", false, 1),
                new PriceTime(5, 6, "02:30", "03:00", false, 2),
                new PriceTime(10, 12, "05:00", "06:00", false, 3),
                new PriceTime(0, 0, null, null, true, 4) // 剩余时间段
        );

        // 生成 48 个时间区间
        List<String> timeSlots = generateTimeSlots();

        // 标记哪些时间段已分配
        boolean[] assigned = new boolean[48];
        List<Map<String, Object>> result = new ArrayList<>();

        // 处理非剩余时间段
        for (PriceTime pt : priceTimes) {
            if (!pt.isTimeRemaining()) {
                for (int i = pt.getStartTimeCode(); i < pt.getEndTimeCode(); i++) {
                    assigned[i] = true;
                    result.add(createResult(i, pt.getType(), timeSlots, priceTypes));
                }
            }
        }

        // 处理剩余时间段
        for (int i = 0; i < 48; i++) {
            if (!assigned[i]) {
                result.add(createResult(i, 4, timeSlots, priceTypes));
            }
        }

        // 合并同类别相邻的时间区间
        List<Map<String, Object>> mergedResult = mergeTimeRanges(result);

        // 输出结果
        mergedResult.forEach(System.out::println);
    }

    // 生成 48 个时间区间
    private static List<String> generateTimeSlots() {
        List<String> slots = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            slots.add(String.format("%02d:%02d", i, 0));
            slots.add(String.format("%02d:%02d", i, 30));
        }
        return slots;
    }

    // 创建每条结果数据
    private static Map<String, Object> createResult(int index, int type, List<String> timeSlots, List<PriceType> priceTypes) {
        Map<String, Object> map = new HashMap<>();
        PriceType priceType = priceTypes.stream()
                .filter(pt -> pt.getType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid type: " + type));
        map.put("startTime", timeSlots.get(index));
        map.put("endTime", index == timeSlots.size() - 1 ? "24:00" : timeSlots.get(index + 1));
        map.put("type", priceType.getName());
        map.put("price", priceType.getPowerPrice());
        map.put("typeCode", type);
        return map;
    }

    // 合并同类别相邻的时间区间
    private static List<Map<String, Object>> mergeTimeRanges(List<Map<String, Object>> intervals) {
        List<Map<String, Object>> merged = new ArrayList<>();

        intervals.sort(Comparator.comparingInt(o -> Integer.parseInt(((String) o.get("startTime")).replace(":", ""))));

        Map<String, Object> current = null;
        for (Map<String, Object> interval : intervals) {
            if (current == null) {
                current = new HashMap<>(interval);
            } else if (current.get("typeCode").equals(interval.get("typeCode")) && current.get("endTime").equals(interval.get("startTime"))) {
                current.put("endTime", interval.get("endTime")); // 合并区间
            } else {
                merged.add(current);
                current = new HashMap<>(interval);
            }
        }
        if (current != null) {
            merged.add(current);
        }

        return merged;
    }
}
