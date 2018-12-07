package com.hacker.jedis.lists;

import com.hacker.jedis.conn.JedisConnectionUtils;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe  测试一些redis的高级命令
 */
public class RedisListTest2 {

    @Test
    public void testList1(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * LPOP: 移除列表的头元素，并返回头元素(移除栈顶的元素)
         */
        jedisCluster.del("test");
        jedisCluster.lpush("test", "hello", "java", "python", "c++", "ruby");
        String remove = jedisCluster.lpop("test"); //移除栈顶的元素 - ruby
        System.out.println("test移除栈顶的元素: " + remove);
        System.out.println("test剩下的元素: " + jedisCluster.lrange("test", 0, -1));
        jedisCluster.del("test");

        //与之对应的还有 rpop: 移除栈底的元素
        jedisCluster.lpush("test2", "hello", "java", "python", "c++", "ruby");
        String test2 = jedisCluster.rpop("test2");
        System.out.println("test2移除栈底的元素: " + test2);
        System.out.println("test2剩下的元素: " + jedisCluster.lrange("test2", 0, -1));

    }
    
    
    @Test
    public void testList2(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * BLPOP ( 与之对应的还有 BLPOP)
         *  它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
         */
        List<String> list1 = jedisCluster.brpop(10, "list1");
        System.out.println(list1); // list1 不存在，所以无法从栈底弹出一个元素，所以他就会一直阻塞，知道10s后返回 []

        jedisCluster.lpush("list1", "1", "ok");
        List<String> list11 = jedisCluster.brpop(10, "list1");
        System.out.println(list11); // [list1, 1] 返回的是 列表的表名 和  栈底的元素

        jedisCluster.del("list1");
        try {
            jedisCluster.lpush("list1", "1", "ok");
            jedisCluster.lpush("list2", "2", "success");
            //虽然参数可以传多个key，但是在集群中是不行的，只能传一个，因为：redis集群中不支持那些需要同时处理多个键的 Redis 命令
            List<String> brpop = jedisCluster.brpop(10, "list1", "list2");
            System.out.println(brpop);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testList3(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * RPOPLPUSH：(这个没有 LPOPRPUSH 哦， 有BRPOPLUSH)
         * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：
         *  将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
         *  将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
         *
         *  如果 source 不存在，值 nil 被返回，并且不执行其他动作。
         *  如果 source 和 destination 相同，则列表中的表尾元素被移动到表头，并返回该元素，
         *  可以把这种特殊情况视作列表的旋转(rotation)操作。
         *
         *  注意：这个方法在集群中不好使，list2 和 list3 在同一个槽内(cluster keyslot key)
         *  但是仍然无法使用这个命令
         *  总结：redis集群中不支持那些需要同时处理多个键的 Redis 命令（比如mset mget rpoplpush.....）
         *
         *  但是他的功能是非常强大的，具体请看http://redisdoc.com
         */
        try {
            jedisCluster.lpush("list2", "hello", "hangzhou", "chifeng", "上海");
            jedisCluster.lpush("list3", "1", "4", "6", "8", "0");

            String rpoplpush = jedisCluster.rpoplpush("list1", "list2");
            System.out.println("源列表弹出的值: " + rpoplpush);
            System.out.println("原列表剩下的值: " + jedisCluster.lrange("list1", 0, -1));
            System.out.println("目标列表的元素: " + jedisCluster.lrange("list2", 0, -1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         *  既然源列表和目标列表可以是同一个，那么使用同一个在集群中是否会报错呢?
         *  测试通过，没有报错，发现源列表的栈底元素 5 被移动了栈顶，所以当 源列表和 目标列表是同一个的时候，可以
         *  实现 一个翻转的效果
         */
        jedisCluster.del("list4");
        jedisCluster.lpush("list4", "1", "2", "3", "4", "5");
        String rpoplpush = jedisCluster.rpoplpush("list4", "list4");
        System.out.println("源列表list4弹出的元素: " + rpoplpush);
        System.out.println("目标列表list4中的元素: " + jedisCluster.lrange("list4", 0, -1));

        String rpoplpush1 = jedisCluster.rpoplpush("list4", "list4");
        System.out.println("源列表list4弹出的元素: " + rpoplpush1);
        System.out.println("目标列表list4中的元素: " + jedisCluster.lrange("list4", 0, -1));


        /**
         * BRPOPLPUSH：
         *   brpoplpush 是 rpoplpush 的阻塞版本，当给定列表 source 不为空时， BRPOPLPUSH 的表现和 RPOPLPUSH 一样。
         *   同样的，集群中无法使用不同的key 的list 测试，因为：redis集群中不支持那些需要同时处理多个键的 Redis 命令
         *   但是 源列表 = 目标列表是可以测试的
         *
         *   当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，或有另一个客户端对 source 执行 LPUSH 或 RPUSH 命令为止。
         *     
         *   超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
         *
         *   返回值：
         *     假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
         *     反之，返回一个含有两个元素的列表，第一个元素是被弹出元素的值，第二个元素是等待时长。
         */
        try {
            //list05 这个key 不存在
            String brpoplpush = jedisCluster.brpoplpush("list05", "list05", 30);
            System.out.println(brpoplpush);  //他会一直阻塞，知道 30s 后返回null（redis 终端返回的是  nil 和等待时长)
        } catch (Exception e) {
            e.printStackTrace();
        }

        //继续测试：在等待的时候，在redis的终端执行 lpush list05 123456 可以看到阻塞这里立刻返回 123456,

    }
}
