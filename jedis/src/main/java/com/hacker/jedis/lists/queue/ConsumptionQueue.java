package com.hacker.jedis.lists.queue;

import com.hacker.jedis.conn.JedisConnectionUtils;
import com.hacker.jedis.entrty.Config;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe 消费队列
 */
public class ConsumptionQueue {

    public static void main(String[] args) {

        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        List<String> ids = RedisQueue.getIds();
        while (true) {
            //RedisQueue 中是lpush 依次放入表头，那么最先放的就被保存进了栈底，rpop 是从栈底取出一个元素
            //String data1 = jedisCluster.rpop("hacker_queue_test");  //这样轮询取出来之后，列表中的元素就没有了
            //String data2 = jedisCluster.rpoplpush("hacker_queue_test", "hacker_queue_test");
            String data2 = jedisCluster.brpoplpush("hacker_queue_test", "hacker_queue_test", 30);
            System.out.println("取出的元素: " + data2);
            if (null != data2) {
                Config config = RedisQueue.getData(data2);
                System.out.println(config);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /**
             *   rpoplpush 源列表  目标列表
             *   原列表 = 目标列表 的时候，他会循环的将数据从栈底移到栈顶，栈中数据一直都在
             */
            if (ids.contains(data2)) {
                jedisCluster.lrem("hacker_queue_test", 0, data2);
            }
        }
    }
}
