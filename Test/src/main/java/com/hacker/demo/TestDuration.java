package com.hacker.demo;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/11/15
 * @project project
 * @describe
 */
public class TestDuration {

    @Test
    public void testDuration() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        Thread.sleep(3000);
        LocalDateTime end = LocalDateTime.now();
        System.out.println(Duration.between(start, end).toMillis() / 1000);

        Integer a = null;
        System.out.println(a + 1);
    }

    @Test
    public void testReduce() {
        List<String> ids = new ArrayList<>();
        ids.add("A");
        ids.add("B");
        ids.add("C");
        ids.add("D");
        ids.add("E");
//        String reduce = ids.stream().reduce((id1, id2) -> id1 + "_" + id2).get();
//        System.out.println(reduce);

        String reduce = ids.stream().reduce("#{}", (dev1, dev2) -> dev1 + dev2);
        System.out.println(reduce);
    }

    @Test
    public void testSubString() {
        String str = "((1<=A相电压<20))";
        str = str.substring(str.indexOf("<=") + 2, str.lastIndexOf("<"));
//        System.out.println(str);

        String content = "(366.4<(#{86170838881013769}) && (#{86170838881013769})<458.0)";
        content = content.substring(content.lastIndexOf("<") + 1, content.lastIndexOf(")"));
//        System.out.println(content);

        String data = "8150.4<(#{86170838881013770}) && (#{86170838881013770})<10188.0";
        data = data.substring(data.lastIndexOf("<") + 1);
        System.out.println("预警: " + data);

        String sb = "(#{88226390603730970}+#{88226724893954057}+#{88226724893954074})>=100.0";
        sb = sb.substring(sb.lastIndexOf(">=" ) + 2);
        System.out.println(sb);
    }
    
    @Test
    public void testConditionBreakPoint(){
        for (int i = 0; i < 100 ; i++) {
            System.out.println(i);
        }
    }
    
    @Test
    public void testAsList(){
        List<String> stationIds = Arrays.asList("52118c4f1cf010c0","52108c4d12f645a0","52108c4d12d1a6a0","52108c4d1002f640","52108c4cbb194a80","52108c4ca35108c1","52108c489aad90c0","52108c44b4eb8a40","52108c4245cb94e0","52108c404c83c660","52108c3e9427d760","52108c3e3799fe61","52108c34ec13ab20","52108c34e1b19660");
        System.out.println(stationIds);
    }

    @Test
    public void testCopy(){
//        Person person = new Person("101","北京", "18368116334");
//        Item item = new Item();
//
//        BeanUtils.copyProperties(person, item);
//        System.out.println(item.getId() + "  -- " + item.getAddress() + " -- " + item.getPhone());
//        System.out.println(item.getName());

       String str =  "2400.0<(#{88226390603730971}) && (#{88226390603730971})<=3000.0";
       str = str.substring(str.lastIndexOf("<=") + 2);
        System.out.println(str);

    }

}

class Item  extends Person{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class Person {

    private String id;

    private String address;

    private String phone;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Person(String id, String address, String phone) {
        this.id = id;
        this.address = address;
        this.phone = phone;
    }

    public Person() {
    }
}