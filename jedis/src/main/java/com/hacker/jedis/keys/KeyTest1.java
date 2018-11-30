package com.hacker.jedis.keys;

import com.hacker.jedis.conn.JedisConnectionUtils;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.TimeUnit;

/**
 * @author Hacker
 * @date：2018/11/30
 * @project project
 * @describe
 * @see http://redisdoc.com
 */
public class KeyTest1 {

    @Test
    public void testKeys1(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
         //是否存在某一个key
        Boolean hacker = jedisCluster.exists("hacker");
        System.out.println(hacker);

        //设置某一个key-value
        jedisCluster.set("hacker", "zhazhahui");
        System.out.println(jedisCluster.get("hacker")); //zhazhahui

        //删除key，删除成功返回1 失败 返回 0
        Long del = jedisCluster.del("hacker");
        System.out.println(del);

        /**
         * 查看所有的key的方法是 keys("*") 但是注意：这个方法在集群中没有，在单机中有
         * 如果真的要获取所有的kye，那么通过集群找到每一个节点，然后通过节点的 keys("*")
         * 方法找到所有的key，最后将每一个节点的keys 都设置到Set 集合中返回
         * 可以参照
         * @see com.hacker.jedis.conn.JedisConnectionUtils
         */

        //查看key的类型
        jedisCluster.set("A", "567");
        System.out.println(jedisCluster.type("A")); //string
        jedisCluster.del("A");

        jedisCluster.lpush("mylist", "0", "1", "2");
        System.out.println(jedisCluster.type("mylist")); //list(链表)
        jedisCluster.del("mylist");

    }
    
    
    @Test
    public void testKeys2() throws  Exception{
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        //设置key的失效时间
        jedisCluster.set("A", "123");
        jedisCluster.expire("A", 10); //10s 后 这个key 自动删除，注意，前提是这个key要存在哦
        while (jedisCluster.exists("A")) {
            // ttl 查看key的剩余时间： -1 表示永不过期  -2 表示已经过期(或者key不存在)
            System.out.println(jedisCluster.ttl("A")); //ttl 查看 key 的失效时间（ttl 查看剩余时间是秒)
            TimeUnit.SECONDS.sleep(1); //睡一秒
        }
        /**
         * 上面测试 可以使用 expire 来为 key 设置过期时间，他的单位是 秒，同样的还有毫秒的命令：pexpire
         *  对应查看剩余时间的命令是 pttl
         */
        jedisCluster.set("C", "123");
        jedisCluster.pexpire("C", 10000); //10s
        while (jedisCluster.exists("C")) {
            System.out.println(jedisCluster.pttl("C")); //注意查看毫秒的剩余时间是 pttl
            TimeUnit.MILLISECONDS.sleep(1000); //睡1000ms
        }

        /**
         * 与上面两个命令类似的还有一个指令 expireAt(redis 命令不区分大小写) 不过他要接受的参数是 unix 的时间戳
         *  这个测试的时候好像有点问题，暂时先不管
         */
        jedisCluster.set("D", "123");
        jedisCluster.expireAt("D", 1543556456000L);
        System.out.println(jedisCluster.ttl("D")); //返回秒
        System.out.println(jedisCluster.pttl("D")); //返回毫秒
    }
    
    @Test
    public void testKeys3(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        jedisCluster.set("M", "123");
        jedisCluster.expire("M", 60); //失效时间 60s
        //不想让这个key 在拥有 失效时间，从易失性 变为 持久性
        jedisCluster.persist("M");//此时返回
        jedisCluster.ttl("M"); // 返回 -1 表示永不过期

        //还有一些命令就不 一一测试了 具体请看：http://redisdoc.com/
    }
}
