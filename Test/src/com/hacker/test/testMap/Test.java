package com.hacker.test.testMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZhaZhaHui
 * @version 1.0.0 @date：2018/9/3
 * @describe
 */
public class Test {

    private static  void  test4(){
        Map<Integer, Double> map = new HashMap<>();
        map.put(1, 1000.0);
        map.put(2, 555.0);
        map.put(3, 900.0);
        map.put(4, 1500.0);
        map.put(5, 400.0);
        System.out.println(map);

        Map<Integer, Double> integerDoubleMap = sortWithMapOfValue(map);
        System.out.println("integerDoubleMap = " + integerDoubleMap);

        Set<Integer> integers = integerDoubleMap.keySet();
        List<Integer> collect = integers.stream().limit(5).collect(Collectors.toList());
        System.out.println("collect = " + collect);

        List<Circuit> list = new ArrayList<>();
        Circuit circuit1 = new Circuit("4", "400");
        Circuit circuit2 = new Circuit("8", "100000");
        Circuit circuit3 = new Circuit("9", "400");
        Circuit circuit4 = new Circuit("1", "100000");
        Circuit circuit5 = new Circuit("2", "100000");
        list.add(circuit1);
        list.add(circuit2);
        list.add(circuit3);
        list.add(circuit4);
        list.add(circuit5);

        List<Circuit> collect1 = list.stream().filter(circuit -> collect.contains(Integer.valueOf(circuit.getType()))).collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);

    }

    /**
     *  将map中数据按着value 降序排序
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    private static <K, V extends Comparable<? super V>> Map<K, V>  sortWithMapOfValue(Map<K, V> map){

        //既然是按照value排序，那么肯定要先拿到每个map中的 entry(entry中包含了key和value)
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return -(o1.getValue().compareTo(o2.getValue()));
            }
        });

        //LinkedHashMap 会保证放入顺序的
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void test3() {
        List<Circuit> list = new ArrayList<>();
        Circuit circuit1 = new Circuit("4", "400");
        Circuit circuit2 = new Circuit("8", "100000");
        Circuit circuit3 = new Circuit("9", "400");
        Circuit circuit4 = new Circuit("1", "100000");
        Circuit circuit5 = new Circuit("2", "100000");
        list.add(circuit1);
        list.add(circuit2);
        list.add(circuit3);
        list.add(circuit4);
        list.add(circuit5);

        List<Circuit> collect = list.stream().filter(circuit -> "40002".equals(circuit.getType())).filter(circuit -> "400".equals(circuit.getRatePower())).collect(Collectors.toList());
        System.out.println(collect);

    }

    private static void test2() {
        Map<String, Double> data = new HashMap<>();

        data.put("1001", 1000.0);
        data.put("1002", 3000.0);
        // System.out.println("data = " + data);

        data.clear();

        data.put("1003", 900.0);
        System.out.println("data = " + data);
    }

    private static List<Map<Long, Double>> test() {
        List<Map<Long, Double>> allSourceList = new ArrayList<>();
        // 提取水的数据，并将其换算成标准煤
        List<Map<Long, Double>> waterList = new ArrayList<>();
        Map<Long, Double> map1 = new HashMap<>();
        map1.put(1001L, 1000.0);
        map1.put(1002L, 555.0);

        Map<Long, Double> map2 = new HashMap<>();
        map2.put(2001L, 700.0);
        map2.put(2002L, 400.0);
        waterList.add(map1);
        waterList.add(map2);

        List<Map<Long, Double>> heatList = new ArrayList<>();
        // map1.clear();
        // map2.clear();
        Map<Long, Double> map3 = new HashMap<>();
        Map<Long, Double> map4 = new HashMap<>();
        map3.put(1001L, 100.0);
        map3.put(1002L, 300.0);
        map4.put(2001L, 800.0);
        map4.put(2002L, 1.0);
        heatList.add(map3);
        heatList.add(map4);

        allSourceList.addAll(waterList);
        allSourceList.addAll(heatList);
        return allSourceList;
    }

    public static void main(String[] args) {
        Test test = new Test();
//        test.test3();

        test4();
    }
}
 class Circuit{

    private String type;

    private String ratePower;

    public Circuit(String type, String ratePower) {
        this.type = type;
        this.ratePower = ratePower;
    }

    public Circuit(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRatePower() {
        return ratePower;
    }

    public void setRatePower(String ratePower) {
        this.ratePower = ratePower;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "type='" + type + '\'' +
                ", ratePower='" + ratePower + '\'' +
                '}';
    }
}