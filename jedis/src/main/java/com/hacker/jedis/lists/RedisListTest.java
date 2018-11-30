package com.hacker.jedis.lists;

import com.hacker.jedis.conn.JedisConnectionUtils;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

/**
 * @author Hacker
 * @date：2018/11/30
 * @project project
 * @describe  redis 的 list 是一个列表，无论数据量多大，对头尾的操作都很快，其中最多可以存 2的32次方减1个元素，即4294967295(40亿)个元素数量。
 *    单键多value
 */
public class RedisListTest {

    @Test
    public void testList1(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();

        /**
         * 将一个或多个值 value 插入到列表 key 的表头
         * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头
         * 链表是可以重复的
         */
        jedisCluster.del("mylist");
        jedisCluster.del("mylist02");
        Long mylist = jedisCluster.lpush("mylist", "1", "2", "9", "3", "0", "0");
        System.out.println(mylist);   //返回的列表的元素个数


        /**
         * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
         * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：
         */
        Long mylist02 = jedisCluster.rpush("mylist02", "8", "6", "5", "8", "1");
        System.out.println(mylist02);
    }
}
