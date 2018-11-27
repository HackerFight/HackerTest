package com.hacker.demo;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Hacker
 * @date：2018/11/1
 * @project project
 * @describe
 */
public class OptionalTest {

    @Test
    public void testReduce(){
        List<String> values = new ArrayList<>();
        values.add("abc");
        values.add("123");
        values.add("opo");
        values.add("world");
        final Optional<String> reduce =values.stream().reduce((v1, v2) -> v1 + "," + v2);
        System.out.println(reduce.get());
    }

    @Test
    public void testOptional() {

        List<String> list = new ArrayList<>();

        //看下源代码就懂了
        List<String> _list = Optional.ofNullable(list).get();
        System.out.println(_list);
    }

    @Test
    public void test2() {
        boolean flag = false;
        long currentMills = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(currentMills);
        LocalDateTime today = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int value = dayOfWeek.getValue();
        flag = (value == 6 || value == 7) ? false : true;
        System.out.println(flag);
    }

    @Test
    public void test3() {
        List<String> codes = new ArrayList<>();
        codes.add("101");
        codes.add("102");
        codes.add("103");
        codes.add("104");
        codes.add("105");

//        codes.forEach(code -> {
//            if (code.equals("101")) {
//                //lambda 中没有设计使用 continue 和 break, 因为他的初衷并不是循环，但是
//                //可以使用return， 这里的 return 和 continue 是一样的效果，退出本次
//                return;
//            }
//            System.out.println(code);
//        });


        // 普通的增强for循环是可以使用 continue 和 break 的
        // 也同样可以使用 return ，只是这里的return 和 break 的效果一样，直接退出
        for (String code : codes) {
            if (code.equals("101")) {
                return;
            }
            System.out.println(code);
        }
    }

    public String returnTest(){
        List<String> codes = new ArrayList<>();
        codes.add("101");
        codes.add("102");
        codes.add("103");
        codes.add("104");
        codes.add("105");

        for(String code : codes) {
             if (code.equals("101")){
                 return code;
             }
        }

        return null;
    }
    
    @Test
    public void test(){
        String s = returnTest();
        System.out.println("s = " + s);
    }
    
    @Test
    public void testDeleteCharAt(){
       StringBuilder sb = new StringBuilder();
       sb.append("hello").append(",").append("world").append(",");

        System.out.println(sb.deleteCharAt(sb.lastIndexOf(",")));
    }

}
