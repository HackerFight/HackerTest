package com.hacker.test.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ZhaZhaHui
 * @version 1.0.0
 * @date：2018/7/11
 * @describe
 */
public class RunnableTest {

    private static List<String> list = Arrays.asList("my", "name", "is", "uber", "and", "uc");

    /**
     * 实现Runnable
     */
    public static void testDemo1() {
        /**
         * java8 以前的实现方式
         */
        Runnable  runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("I am running....");
            }
        };

        new Thread(runnable).start();


        /**
         *   java8 的实现方式
         */
        new Thread(() -> System.out.println("Lambda running....")).start();
    }

    /**
     *  比较
     */
    public  static void oldCalculator() {
        /**
         * 对一个String 的list 进行排序，使用老的方法
         */
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if (a.charAt(1) > b.charAt(1)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        System.out.println(list);
    }

    public static void newCalculator() {
        Collections.sort(list, (a, b) -> a.charAt(1) > b.charAt(1) ? 1 : -1);
        System.out.println(list);

    }

    public static void main(String[] args) {
//        testDemo1();
        oldCalculator();
        newCalculator();

        list.forEach((str) -> System.out.println(str));
    }
}
