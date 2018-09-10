package com.hacker.model.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe
 */
public class MainTest {
    

    public void testMySingleton(){
        //        for (int i = 0; i < 10 ; i++) {
//            System.out.println(MySingleton.getInstance());
//        }

        //多线程环境
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + " -- " + MySingleton.getInstance());
                }
            });
        }

        pool.shutdown();
    }

    public static void testMySingletonPattern(){
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //测试发现确实有问题,通过枷锁来解决问题
                System.out.println(MySingletonPattern.getInstance());
            }).start();
        }

    }

    public static void main(String[] args) {
        testMySingletonPattern();
    }
}
