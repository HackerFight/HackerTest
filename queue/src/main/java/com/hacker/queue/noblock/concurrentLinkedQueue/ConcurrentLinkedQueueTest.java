package com.hacker.queue.noblock.concurrentLinkedQueue;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Hacker
 * @date：2018/12/7
 * @see ConcurrentLinkedQueue
 *  一个基于链接节点的无界线程安全队列。此队列按照 FIFO（先进先出）原则对元素进行排序。队列的头部 是队列中时间最长的元素。队列的尾部 是队列中时间最短的元素。
 *  新的元素插入到队列的尾部，队列获取操作从队列头部获得元素。当多个线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。此队列不允许使用 null 元素。
 */
public class ConcurrentLinkedQueueTest {

    private static final ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

    private static final ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();

    private static final ConcurrentLinkedQueue taskQueue = new ConcurrentLinkedQueue();

    @Test
    public void test1(){
        /**
         *  add 和 offer 一样，都是向队列中添加元素
         *  其中：不可以放 null， 报错：NullPointerException
         */
        concurrentLinkedQueue.add("Hello");
        concurrentLinkedQueue.offer("World");
        System.out.println("队列是否为空: " + concurrentLinkedQueue.isEmpty());
        /**
         *  poll() 获取队列的头元素，并将其移除队列，如果队列为空，返回 null
         */
        System.out.println("从队列总poll一个值: " + concurrentLinkedQueue.poll());
        System.out.println("队列是否为空: " + concurrentLinkedQueue.isEmpty());
    }
    
    @Test
    public void test2(){
        queue.add("abc");
        queue.offer("123");

        /**
         * peek()  获取队列的头元素，但是不移除队列
         */
        System.out.println(queue.peek());
        System.out.println(queue.size());  // 2 说明确实不移除队列

        /**
         *  remove() 源码中他是 poll 一个，然后移除掉， poll 是取队列的头元素
         *  如果队列是空的： NoSuchElementException
         */
        System.out.println(queue.remove());//
        System.out.println(queue.size());  // 1 说明确实移除了队列的头元素

        /**
         * remove(Object obj) 移除一个具体的元素
         */
        System.out.println(queue.remove("123")); //true

        System.out.println(Integer.MAX_VALUE);
    }

    
    @Test
    public void test3(){
        LocalDateTime start = LocalDateTime.now();
        /**
         *  验证： size or isEmpty 的效率
         *  如果此队列包含的元素数大于 Integer.MAX_VALUE，则返回 Integer.MAX_VALUE。
         *  需要小心的是，与大多数 collection 不同，此方法不是 一个固定时间操作。由于这些队列的异步特性，确定当前的元素数需要进行一次花费 O(n) 时间的遍历。
         *  所以在需要判断队列是否为空时，尽量不要用 queue.size()>0，而是用 !queue.isEmpty()
         */
        for (int i = 0; i < 300000000L ; i++) {
            taskQueue.add(i);
        }
        LocalDateTime end = LocalDateTime.now();
        System.out.println(Duration.between(start, end).toMillis());
        System.out.println(taskQueue.size());
    }
    
}
