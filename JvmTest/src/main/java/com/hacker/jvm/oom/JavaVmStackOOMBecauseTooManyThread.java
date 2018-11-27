package com.hacker.jvm.oom;

/**
 * @author Hacker
 * @date：2018/11/13
 * @project project
 * @describe  建立过多线程导致的内存溢出： 执行这段代码前 记得保存当前工作，因为在Windows平台的虚拟机上 java 线程是映射到操作系统的内核线程上的
 *             所以这段代码可能导致操作系统假死（亲测）
 *     VM Args: -Xss2M(这时候不妨设置大一些)
 *
 *     P55
 *
 *     此方法千万不要执行了  死机
 */
public class JavaVmStackOOMBecauseTooManyThread {

    private void dontStop(){
        while (true) {}
    }

    public void stackLeakByThread(){
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {

        JavaVmStackOOMBecauseTooManyThread oom = new JavaVmStackOOMBecauseTooManyThread();
        oom.stackLeakByThread();
    }
}

