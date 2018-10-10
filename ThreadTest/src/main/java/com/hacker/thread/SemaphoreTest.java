package com.hacker.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Hacker
 * @date：2018/10/9
 * @project project
 * @describe Semaphore 可以维护当前访问自身线程的个数，并提供了同步机制，使用 Semaphore 可以控制同时访问资源的
 * 线程个数，例如：实现一个文件允许的并发访问数
 */
public class SemaphoreTest {

    public static void main(String[] args) {

        //创建一个线程池
        final ExecutorService executorService = Executors.newCachedThreadPool();
        //开启了三个信号灯，表示只能同时有三个线程操作
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        /**
                            * 获得信号灯，就好比获得了一张门牌，然后进入厕所，关门.
                            * 注意：这里执行一次，信号灯就会减少1，就相当于有人用了一盏
                            */
                        System.out.println("允许进来的线程: " + semaphore.availablePermits());
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程 " + Thread.currentThread() + "已进入; 当前已有 " + (3 - semaphore.availablePermits()) + " 个位置被占了");

                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程 " + Thread.currentThread() + "即将离开");
                    /**
                     * 执行完这个方法后，availablePermits() 就会加1，表示腾出来一个厕所
                     */
                    semaphore.release();
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
