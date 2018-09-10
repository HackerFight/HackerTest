package com.hacker.test.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZhaZhaHui
 * @version 1.0.0
 * @date：2018/8/27
 * @describe
 */
public class ComparatorTest {

    private static <K, V extends Comparable<? super V>> Map<K, V> sortWithMapOfValue(Map<K, V> map){

        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Map<K, V> resultMap = new LinkedHashMap<>();
        for (Map.Entry<K,V> kvEntry : list) {
             resultMap.put(kvEntry.getKey(), kvEntry.getValue());
        }

        return resultMap;
    }

    public static void main(String[] args) {

        List<AlarmRecord> list = new ArrayList<>();

        AlarmRecord alarmRecord = new AlarmRecord("1", 1535126400000L, "1号"); //25
        AlarmRecord alarmRecord2 = new AlarmRecord("2", 1535212800000L, "2号"); //26
        AlarmRecord alarmRecord3 = new AlarmRecord("3", 1535299200000L, "3号"); //27
        list.add(alarmRecord);
        list.add(alarmRecord2);
        list.add(alarmRecord3);

        Long happenTime = list.stream().sorted(Comparator.comparing(AlarmRecord::getHappenTime).reversed()).collect(Collectors.toList()).get(0).getHappenTime();
        System.out.println("happenTime = " + happenTime);
    }
}


class AlarmRecord {
    private String id;

    private Long happenTime;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Long happenTime) {
        this.happenTime = happenTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlarmRecord(String id, Long happenTime, String name) {
        this.id = id;
        this.happenTime = happenTime;
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlarmRecord{" +
                "id='" + id + '\'' +
                ", happenTime='" + happenTime + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}