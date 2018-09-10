package com.hacker.test.java8.lambda;

import java.util.TreeSet;

/**
 * @author ZhaZhaHui
 * @version 1.0.0
 * @date：2018/7/18
 * @describe
 *
 *     -> : 这是lambda 的操作符
 *     左侧：指定表达式所需要的所有参数
 *     右侧：指定lambda表达体
 *
 *     几种写法：
 *      1.无参数无返回值：如，创建多线程
 *         () -> System.out.println("hello lambda");
 *
 *      2.一个参数，无返回值
 *         (args) -> System.out.println(args); //注意：如果只有一个参数，() 可以省略
 *
 *      3.有参数有返回值的
 *         (x, y) -> {
 *              return x + y;
 *         }
 *
 *       注意：如果lambda体 只有一句时， return 和 {} 都可以省了，于是，简写为：
 *         (x, y) -> x + y;
 */
public class LambdaTest {

    public static void test1(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("old .....");
            }
        };

        Runnable runnable1 = () -> System.out.println("new .....");

        new Thread(runnable).start();
        new Thread(runnable1).start();
    }


    public static void test2(){
//        TreeSet<String> treeSet = new TreeSet<>(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return Integer.compare(o1.length(),o2.length());
//            }
//        });

//        TreeSet<String> treeSet = new TreeSet<>((o1, o2) -> {
//              return Integer.compare(o1.length(), o2.length());
//            }
//        );

        TreeSet<String> treeSet = new TreeSet<>((x, y) -> x.length() - y.length());

        // TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("abc");
        treeSet.add("mnq");
        treeSet.add("abcd");
        treeSet.add("f");
        treeSet.add("x");
        System.out.println(treeSet);
    }

    public  static  String toUpperString(StringUpperFunction<String> fun, String str){
        return fun.getValue(str);
    }

    public static void test3(){
        String string = toUpperString((strs) -> strs.toUpperCase(), "abcde");
        System.out.println(string);
    }


    public static void test4(){

    }

    public static void main(String[] args) {
//        test2();

//         test3();

    }
}
