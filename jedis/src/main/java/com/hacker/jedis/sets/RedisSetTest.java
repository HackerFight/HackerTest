package com.hacker.jedis.sets;

import com.hacker.jedis.conn.JedisConnectionUtils;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Set;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe
 */
public class RedisSetTest {

    @Test
    public void testSet1() {
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         *  SADD key member [member ...]
         *   将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
         *   假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
         *   当 key 不是集合类型时，返回一个错误。
         */
        Long set1 = jedisCluster.sadd("set1", "1", "1", "2", "2", "3", "3");
        System.out.println("返回插入的个数：" + set1);

        /**
         * smembers: 根据key获取集合（set)
         */
        Set<String> set11 = jedisCluster.smembers("set1");
        System.out.println("smembers 取出的元素: " + set11);
        System.out.println(jedisCluster.type("set1")); // set

        /**
         * sismember : 判断集合(set) 中是否含有这个元素
         */
        Boolean set12 = jedisCluster.sismember("set1", "1");
        System.out.println(set12);

        /**
         * scard: 获取集合中元素的个数
         */
        Long set13 = jedisCluster.scard("set1");
        System.out.println("集合中元素的个数: " + set13);

        /**
         * srem: （list 中是 lrem) 移除集合中的元素
         */
        Long set14 = jedisCluster.srem("set1", "5");
        System.out.println(set14); //因为没有5，返回没有移除，所以返回0

        Long set15 = jedisCluster.srem("set1", "3");
        System.out.println(set15); //移除掉了 3， 返回移除的个数 1

        Set<String> set16 = jedisCluster.smembers("set1");
        System.out.println(set16); // [1, 2] 移除掉了 3

        Long set17 = jedisCluster.srem("set1", "1", "2");//移除多个
        System.out.println(set17);  // 返回2，移除了2个
        System.out.println(jedisCluster.smembers("set1"));  // [] 全部都被移除了

        //差集: 求两个集合A和B的差集是指在A中删除A和B中共有的元素
    }
    
    @Test
    public void testSet2(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * SRANDMEMBER key [count]
         *   如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
         *   从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数：
         *
         *  如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
         *  如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
         *  该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动。
         */
        jedisCluster.sadd("set1", "1", "ok", "1", "4", "6");
        String set1 = jedisCluster.srandmember("set1"); //不指定 count 随机返回一个 元素
        System.out.println(set1);
        System.out.println(jedisCluster.smembers("set1")); // 对原来的集合没有任何的影响

        List<String> set11 = jedisCluster.srandmember("set1", 3);
        System.out.println(set11);  //随机返回3个元素,注意返回的是list
        System.out.println(jedisCluster.smembers("set1")); // 对原来的集合没有任何的影响

        while (true) {
            List<String> set12 = jedisCluster.srandmember("set1", -2); //注意返回的是list
            String s0 = set12.get(0);
            String s1 = set12.get(1);
            if (s0.equals(s1)) {
                System.out.println(set12);  //当 count 为负数的时候，返回的数据确实是可以重复的
                break;
            }
        }

        System.out.println("----------------------------------------------------------------");

        jedisCluster.sadd("set2", "ok", "2", "99", "hello", "2");
        System.out.println(jedisCluster.smembers("set2")); //输出集合中的元素，他不像list那样指定起止索引
        System.out.println(jedisCluster.scard("set2")); //返回集合中长度
        System.out.println(jedisCluster.srandmember("set2",2)); //虽然返回2个元素，不影响原来的集合
        System.out.println(jedisCluster.srem("set2", "hello")); //从集合中移除 hello
        System.out.println(jedisCluster.sismember("set2", "hello")); //是否存在某一个元素
        jedisCluster.del("set2");
        System.out.println("-----------------------------------------------------------------");

    }
    
    @Test
    public void testSet3(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * SPOP
         *  移除并返回集合中的一个随机元素。
         *  如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
         */
        jedisCluster.sadd("set3", "hello", "66", "my", "ok", "24");
        String set3 = jedisCluster.spop("set3"); //随机从集合中移除一个元素，集合中将无此元素
        System.out.println(set3);
        System.out.println("集合中剩下的元素: " + jedisCluster.smembers("set3"));
        jedisCluster.del("set3");

        System.out.println("-----------------------------------------------------------------");

        /**
         * <pre>
         * SMOVE
         *   SMOVE source destination member
             将 member 元素从 source 集合移动到 destination 集合。SMOVE 是原子性操作。
             如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。否则，
             member 元素从 source 集合中被移除，并添加到 destination 集合中去。

             当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
             当 source 或 destination 不是集合类型时，返回一个错误。

             妈的：忘了，redis集群中不支持那些需要同时处理多个键的 Redis 命令
           </pre>
         */
        try {
            jedisCluster.sadd("set4", "hello", "66", "my", "ok", "24");
            final Long smove = jedisCluster.smove("set4", "set5", "world");
            System.out.println("源集合: " + jedisCluster.smembers("set4"));
            System.out.println("目的集合: " + jedisCluster.smembers("set5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSet4(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();

        /**
         * <pre>
         * 差集：sdiff  【求两个集合A和B的差集是指在A中删除A和B中共有的元素】
              SDIFF set01 set02    #4,5 差集：就是用第一个集合 - 第二个与第一个的交集部分
                    如：set01 是 1 2 3
                        set02 是 3 4 5
                     set01 和 set02 的差集：1 2
           并集:sunion
              SUNION set01 set02   #2个集合的所有内容
           交集:sinter
              SINTER set01 set02   #1 2 3

           注意：以上命令都不会影响原来的集合
           </pre>
         */
        try {
            jedisCluster.sadd("set5", "JingDong", "TianMao", "PinDuoDuo");
            jedisCluster.sadd("set6", "TianMao", "TaoBao", "JuMei");
            //差集(又忘了，在集群中无法使用多个key的命令）。。。。。。
            Set<String> sdiff = jedisCluster.sdiff("set5", "set6");
            System.out.println("差集结果: " + sdiff);
            System.out.println("set5元素: " + jedisCluster.smembers("set5"));
            System.out.println("set6元素: " + jedisCluster.smembers("set6"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //同样也无法使用多个key的命令，但是他会将处理结果，保存进 set8中
            jedisCluster.sadd("set6", "12", "34", "55");
            jedisCluster.sadd("set7", "12", "98", "op");
            jedisCluster.sdiffstore("set8", "set6", "set7");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

