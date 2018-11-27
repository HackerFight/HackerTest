package com.hacker.demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hacker
 * @date：2018/11/8
 * @project project
 * @describe
 */
public class ThreadTest implements  Runnable {



    @Override
    public void run() {
       while (true){
           System.out.println(new Random().nextInt(10000));
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.submit(new ThreadTest());
        executorService.shutdown();
    }

}
