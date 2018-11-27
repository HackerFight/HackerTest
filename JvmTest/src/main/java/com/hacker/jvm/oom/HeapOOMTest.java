package com.hacker.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/11/12
 * @project project
 * @module JvmTest
 * @describe 堆内存溢出测试
 *  VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 *  深入理解Java虚拟机P51
 */
public class HeapOOMTest {

    //静态内部类
    static class OOMObject {

    }

    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<>();
        while (true) {
           list.add(new OOMObject());
        }
    }
}
