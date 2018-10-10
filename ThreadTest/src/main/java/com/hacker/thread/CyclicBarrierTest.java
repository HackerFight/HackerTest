package com.hacker.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hacker
 * @date：2018/10/9
 * @project project
 * @describe  表示大家彼此等待，大家集合好后才开始出发，分散活动后又在指定地点碰面。
 *             这就好比公司员工利用周末时间集体郊游一样，先各自从家出发到公司集合，都准备好后在从公司出发去公园游玩。在指定地点
 *             集合后在开始同时用餐。
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //3个人约好旅游
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 3 ; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    try {
                        //从家里出发
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("此次春游共有 " + cyclicBarrier.getParties() + " 人");
                    System.out.println("线程 " + Thread.currentThread().getName() + " 即将到达集合地点公园");
                    System.out.println("目前已有 " + (cyclicBarrier.getNumberWaiting() + 1) + " 人到达; " + ((cyclicBarrier.getNumberWaiting() + 1) == 3 ? "到齐了可以出发": "等着"));

                    try {
                        /**
                            *  每次执行 await() ，内部维护的变量 count 就会 -1
                            *  只有当 3个人都到了这里，才会往下执行
                            */
                        cyclicBarrier.await();
                    } catch (InterruptedException  | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    try {
                        //睡眠有时候可以让 线程有序的去执行，不然效果可能会不一样
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /**
                       *  接下来3个人 开始去餐厅，到餐厅集合
                       */
                    System.out.println("此次春游共有 " + cyclicBarrier.getParties() + " 人");
                    System.out.println("线程 " + Thread.currentThread().getName() + " 即将到达集合地点餐厅");
                    System.out.println("目前已有 " + (cyclicBarrier.getNumberWaiting() + 1) + " 人到达; " + ((cyclicBarrier.getNumberWaiting() + 1) == 3 ? "到齐了可以用餐了": "等着"));

                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException  | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.submit(runnable);
        }
        executorService.shutdown();
    }
}
