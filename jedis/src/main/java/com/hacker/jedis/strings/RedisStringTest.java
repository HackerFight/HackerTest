package com.hacker.jedis.strings;

import com.hacker.jedis.conn.JedisConnectionUtils;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @author Hacker
 * @date：2018/11/30
 * @project project
 * @describe
 */
public class RedisStringTest {

    @Test
    public void testStrings1() {
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        //设置 key - value
        String a = jedisCluster.set("A", "123");
        System.out.println("设置一个key-value = " + a); //设置成功返回OK

        //根据 key 获取 value 
        String value = jedisCluster.get("A");
        System.out.println("value = " + value);

        //删除key（key-value 都删除）
        Long del = jedisCluster.del("A");
        System.out.println("del = " + del); //删除成功返回 1

        //append 命令
        jedisCluster.set("A", "Hello");
        jedisCluster.append("A", "-World");
        System.out.println(jedisCluster.get("A"));  //Hello-World

        //strlen 命令（ch查看 value 的长度)
        System.out.println(jedisCluster.strlen("A"));
    }

    @Test
    public void testStrings2(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        //测试递增&递减（点击 + 1 或者 -1）
        jedisCluster.set("A", "1");
        Long add = jedisCluster.incr("A");
        System.out.println("add = " + add); // 2 每次执行 增加1

        jedisCluster.set("B", "10");
        jedisCluster.decr("B");
        System.out.println("decr = " + jedisCluster.get("B")); // 9 每次执行 - 1

        //按照指定频率递增&递减
        jedisCluster.set("A", "1");  //注意：key 相同会覆盖
        jedisCluster.incrBy("A", 5);
        System.out.println("按照执行频率递增：" + jedisCluster.get("A"));

        jedisCluster.set("B", "10");
        jedisCluster.decrBy("B", 19);
        System.out.println("按照指定频率递减: " + jedisCluster.get("B")); // -9
    }

    @Test
    public void testStrings3(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        //getrange 命令 类似与 substring
        jedisCluster.set("A","Hello-World");
        //-1表示倒是第一位，-2 表示倒数第二位
        String a = jedisCluster.getrange("A", 0, -1); //Hello-World
        System.out.println(a);

        //他是包含首尾的，首位是 0
        String a1 = jedisCluster.getrange("A", 0, 4); //Hello
        System.out.println("a1 = " + a1);

        //setrange: 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。（类似于 replace)
        jedisCluster.setrange("A",6, "Redis"); // Hello-Redis
        System.out.println(jedisCluster.get("A"));

        jedisCluster.setrange("A",6, "OK"); // Hello-OKdis  
        System.out.println(jedisCluster.get("A"));
    }
    
    @Test
    public void testStrings4(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        //setnx: 如果不存在就设置，如果存在就不设置
        Long myKey = jedisCluster.setnx("MyKey", "123456");  // 成功返回 1
        System.out.println("myKey = " + myKey);

        Long setnx = jedisCluster.setnx("MyKey", "Haha");  // 失败返回 0
        System.out.println("setnx = " + setnx);

        /**
         * setex (设置key的同时并设置失效时间)
         * 他是 set 和 expire 的组合
         */
        jedisCluster.setex("Hacker", 30, "HelloWorld");

        //如果一个key存在，没有失效时间，然后我使用 setex 会怎样?
        jedisCluster.set("A123456", "ZhaZha");
        System.out.println(jedisCluster.ttl("A123456")); // -1

        jedisCluster.setex("A123456", 20, "北海");
        System.out.println(jedisCluster.get("A123456")); //北海，使用ttl 发现，过期时间确实是20s，所以他会覆盖

        //与 setex 类似的还有 psetex 他是以毫秒为过期时间
        jedisCluster.psetex("ABC0", 10000, "HHHH");
    }

    /**
     * 注意
     */
    @Test
    public void testStrings5(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        //一次性设置多个key-value
        //注意在集群中尽量不要使用 一次可以设置多个值的，因为redis存储是分槽的，一旦对key 取hash后不再一个槽内，那么会报错
        String mset = jedisCluster.mset("A", "123");
        System.out.println(mset);

        // B 和 C 是我在redis 终端设置的，他们不再同一个节点上
        List<String> mget = jedisCluster.mget("A", "B", "C");  //报错： ..... because keys have different slots.
        System.out.println(mget);

        /**
         * X 和 AA 位于同一个节点上，但是仍然报错，所以在redis集群中，这种多设置多取值的就不要用了
         */
        List<String> mget2 = jedisCluster.mget("X", "AA");  //报错： ..... because keys have different slots.
        System.out.println(mget2);
    }
}
