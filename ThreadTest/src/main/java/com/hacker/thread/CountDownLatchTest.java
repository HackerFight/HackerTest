package com.hacker.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hacker
 * @date：2018/10/9
 * @project project
 * @describe   犹如一个倒计时计数器，调用 CountDownLatch 对象的 countDown() 方法会减1，当减到 0 时，所有等待的线程都开始执行。
 *              可以实现一个人(也可以是多个人) 等待其他所有人都来通知他。 还可以实现一个人通知多个人的效果，类似 裁判一声口令，运动员同时
 *              开跑，或者所有运动员都跑到了终点后裁判才可以公布结果，用这个功能做百米赛跑的游戏不错~ 还可以实现一个文件需要多个领导签字
 *              才可以继续往下实施的情况
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception{
       final ExecutorService executorService = Executors.newCachedThreadPool();
       //裁判
       final CountDownLatch cdOrder = new CountDownLatch(1);
       //运动员
       final CountDownLatch cdAnswer = new CountDownLatch(3);

        for (int i = 0; i < 3 ; i++) {
            System.out.println("For Loop .....");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程 " + Thread.currentThread() + " 正准备接受指令");
                    try {
                        //等待运动员准备,三个运动员线程到了这里就阻塞了，只有当裁判 = 0 的时候才可以执行
                        cdOrder.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程 " + Thread.currentThread() + " 做好准备");
                    cdAnswer.countDown();
                }
            };

            executorService.submit(runnable);
        }

        //主线程
//        Thread.sleep(new Random().nextInt(3000));
        System.out.println("裁判即将发布指令" + Thread.currentThread());
        cdOrder.countDown();

        //主线程到了这里也要阻塞，等待三个运动员执行完
        cdAnswer.await();
        System.out.println("所有运动员都已经准备好，可以开始了.....");

        executorService.shutdown();
    }
}
