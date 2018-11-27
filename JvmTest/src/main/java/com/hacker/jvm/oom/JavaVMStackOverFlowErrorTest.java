package com.hacker.jvm.oom;

/**
 * @author Hacker
 * @date：2018/11/12
 * @project project
 * @describe  Java虚拟机栈和本地方法栈的OOM测试
 *   VM Args: -Xss128k
 *
 *   深入理解Java虚拟机P53
 */
public class JavaVMStackOverFlowErrorTest {

    private int stackLength  = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackOverFlowErrorTest stackOOM = new JavaVMStackOverFlowErrorTest();
        try {
            stackOOM.stackLeak();
        } catch (Exception e) {
            System.out.println(" stack length: " + stackOOM.stackLength);
            throw e;
        }
    }

}
