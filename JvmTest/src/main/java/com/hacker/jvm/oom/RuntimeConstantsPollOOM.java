package com.hacker.jvm.oom;

/**
 * @author Hacker
 * @date：2018/11/13
 * @project project
 * @describe 运行时常量池内存溢出（这个在jdk6中可以演示 具体参照P56)
 */
public class RuntimeConstantsPollOOM {

    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
