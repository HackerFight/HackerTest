package com.hacker.jedis.lists;

import com.hacker.jedis.conn.JedisConnectionUtils;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @author Hacker
 * @date：2018/11/30
 * @project project
 * @describe redis 的 list 是一个列表，无论数据量多大，对头尾的操作都很快，其中最多可以存 2的32次方减1个元素，即4294967295(40亿)个元素数量。
 * 单键多value
 */
public class RedisListTest1 {

    @Test
    public void testList1() {
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

        /**
         *  lrange 查看list 中的元素，类似于 foreach
         *  0 到 -1 表示查看全部，因为在redis 中最后一位按 -1来，倒数第二位是 -2 ....
         *  其中包括起止边界
         */

        List<String> mylist021 = jedisCluster.lrange("mylist02", 0, -1);
        System.out.println(mylist021);
        //查看list中前3个元素
        List<String> mylist022 = jedisCluster.lrange("mylist02", 0, 2); // 0 1 2 前三个元素
        System.out.println(mylist022);
        //查看list 中的第一个元素
        List<String> mylist023 = jedisCluster.lrange("mylist02", 0, 0);  // 0 0 前1个元素
        System.out.println(mylist023);
        //查看list 中最后两个元素
        List<String> mylist025 = jedisCluster.lrange("mylist02", -2, -1);
        System.out.println(mylist025);

        // lindex : 按照索引下标 获取元素
        String mylist024 = jedisCluster.lindex("mylist02", 0);
        System.out.println(mylist024);
        String mylist026 = jedisCluster.lindex("mylist02", 100);
        System.out.println(mylist026);  //没有返回  null

        /**
         *  llen ：查看 list 中元素的个数
         */
        Long mylist027 = jedisCluster.llen("mylist02");
        System.out.println(mylist027);

    }

    @Test
    public void testList2() {
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();

        jedisCluster.del("mylist");
        jedisCluster.del("mylist02");
        jedisCluster.del("mylist03");
        jedisCluster.lpush("mylist", "1", "2", "9", "3", "0", "0");

        /**
         *  lrem: 根据参数 count 的值，移除列表中与参数 value 相等的元素。（类似 remove)
         *  count 的值可以是以下几种：
         *     count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
         *     count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
         *     count = 0 : 移除表中所有与 value 相等的值。
         *   lrem(String key, long count, String value)
         *
         *   注意：此时会将list中元素彻底移除掉
         */

        // count = 0 从 list 中移除所有 value = "0" 的元素
        Long delCount = jedisCluster.lrem("mylist", 0, "0");
        System.out.println("mylist移除了多少个： " + delCount);  // 3 9 2 1
        System.out.println("mylist最后的结果： " + jedisCluster.lrange("mylist", 0, -1));

        jedisCluster.lpush("mylist02", "9", "8", "0", "9", "1");
        /**
         *  count = 1 > 0 从表头向表尾搜索 移除 value = 9 的元素，移除多少个 ? = count
         */
        Long mylist02 = jedisCluster.lrem("mylist02", 1, "9");
        System.out.println("mylist02移除了多少个： " + mylist02);  // 移除了 1 个 ( 1 0 8 9 )
        System.out.println("mylist02最后的结果： " + jedisCluster.lrange("mylist02", 0, -1)); // [1, 0, 8, 9]

        //从左至右 依次 插入表尾(那么 最后一个 8 肯定在表尾了)（所以最后是 8 4 6 6 6）
        jedisCluster.rpush("mylist03", "6", "6", "6", "4", "8", "6");
        //count = -3 < 0  从表尾向表头搜索 移除 value = "6" 的元素，移除 count(3) 个
        Long mylist03 = jedisCluster.lrem("mylist03", -3, "6");
        System.out.println("mylist03移除多少个: " + mylist03); // 3
        System.out.println("mylist03还剩什么: " + jedisCluster.lrange("mylist03", 0, -1)); // [6, 4, 8]

        System.out.println("-----------------------------------------------------------------------------");

        /**
         *  ltrim: 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
         *  举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。
         *
         *  当 key 不是列表类型时，返回一个错误。
         *
         *  超出范围的下标：
         *   超出范围的下标值不会引起错误。
         *   如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，或者 start > stop ， LTRIM 返回一个空列表(因为 LTRIM 已经将整个列表清空)。
         *   所以说清空一个list 最快的办法就是 ltrim key 1 0 (让 start 大于 stop ) 貌似 ltrim key -1 0 也行
         *   也特别注意不要乱用
         *   start 和 stop 都比列表的最大下标要大，并且 start < stop ，此时也会清空列表的
         */
        jedisCluster.del("mykey");
        jedisCluster.lpush("mykey", "8", "5", "0", "1", "5", "0");
        //情况 1： 常见情况， start 和 stop 都在列表的索引范围之内
        String mykey = jedisCluster.ltrim("mykey", 1, -1);  //移除了表头的 0
        System.out.println(mykey); //返回OK
        System.out.println("情况1还剩下什么: " + jedisCluster.lrange("mykey", 0, -1));
        jedisCluster.del("mykey");

        //情况 2： stop 比列表的最大下标还要大
        jedisCluster.lpush("mykey", "8", "5", "0", "1", "5", "0");
        jedisCluster.ltrim("mykey", 2, 10000); //此时他会把 列表的最大下表作为 stop （ llen list 减 1）
        System.out.println("情况2还剩下什么: " + jedisCluster.lrange("mykey", 0, -1));
        jedisCluster.del("mykey");

        //情况 3：start 和 stop 都比列表的最大下标要大，并且 start < stop
        jedisCluster.lpush("mykey", "8", "5", "0", "1", "5", "0");
        jedisCluster.ltrim("mykey", 1000, 2000);
        System.out.println("情况3还剩下什么: " + jedisCluster.lrange("mykey", 0, -1)); // [] 清空了列表
        jedisCluster.del("mykey");

        //情况 4：start 和 stop 都在列表的索引范围之内， 但 start > stop
        jedisCluster.lpush("mykey", "8", "5", "0", "1", "5", "0");
        jedisCluster.ltrim("mykey", 2, 1);
        System.out.println("情况4还剩下什么: " + jedisCluster.lrange("mykey", 0, -1)); // [] 清空了列表
        jedisCluster.del("mykey");

        //Tips: 清空一个列表最快的方法就是让 start > stop
    }


    @Test
    public void testList3() {
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * LPUSHX: (与之对应的还有 RPUSHX [放入表尾])
         *    将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
         *    和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
         * 注意：在jedis 中这个方法的value 虽然是可变参数，但是不能传多个，只能传一个，在redis中本身也是只能传一个
         * jedis 版本是2.9.0，redis 版本是（3.2 使用 redis-server -v 可以查看）
         */
        jedisCluster.del("mykey2");
        Long mykey2 = jedisCluster.lpushx("mykey2", "5");
        System.out.println(mykey2);  //返回值： LPUSHX 命令执行之后，表的长度。
        System.out.println(jedisCluster.lrange("mykey2", 0, -1)); // []
        //然后 lpush 一个 列表，然后 在继续测试 lpushx ，发现每次插入的值都是放在了表头
        jedisCluster.lpush("mykey2", "1", "5", "0"); //lpush 如果列表不存在，他会新建一个
        Long count1 = jedisCluster.lpushx("mykey2", "8");
        System.out.println("将 8 放入表头，列表的长度：" + count1);
        System.out.println("列表mykey2的内容：" + jedisCluster.lrange("mykey2", 0, -1));

        Long count2 = jedisCluster.lpushx("mykey2", "2");
        System.out.println("将 2 放入表头，列表的长度：" + count2);
        System.out.println("列表mykey2的内容：" + jedisCluster.lrange("mykey2", 0, -1));
        jedisCluster.del("mykey2");

        /**
         * LSET：
         *   将列表 key 下标为 index 的元素的值设置为 value 。
         *   当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
         */
//        String lset = jedisCluster.lset("mykey3", 0, "abc"); //JedisDataException: ERR no such key
//        System.out.println(lset);
//        System.out.println(jedisCluster.lrange("mykey3", 0 , -1));

        jedisCluster.lpush("mykey3", "6", "3", "1");
        System.out.println(jedisCluster.lrange("mykey3", 0, -1)); //[1, 3, 6]
        jedisCluster.lset("mykey3", 0, "abc"); //将 index = 0 的元素 value 置为 "abc"
        System.out.println(jedisCluster.lrange("mykey3", 0, -1)); // [abc, 3, 6]
        jedisCluster.del("mykey3");

        /**
         * <pre>
         * LINSERT: [ LINSERT key BEFORE|AFTER pivot value ]
         *   将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
             当 pivot 不存在于列表 key 时，不执行任何操作。
             当 key 不存在时， key 被视为空列表，不执行任何操作。
             如果 key 不是列表类型，返回一个错误。

             返回值:
             如果命令执行成功，返回插入操作完成之后，列表的长度。
             如果没有找到 pivot ，返回 -1 。
             如果 key 不存在或为空列表，返回 0 。
         * <pre>
         */
        jedisCluster.del("mykey4");
        //这句话的意思就是：将  Three 插入到   mykey4 这个列表的  Hello  之后
        Long linsert = jedisCluster.linsert("mykey4", BinaryClient.LIST_POSITION.AFTER, "Hello", "Three");
        System.out.println(linsert);  //key 不存在 返回 0

        jedisCluster.rpush("mykey4", "Hello", "World"); //依次放入表尾
        Long linsert1 = jedisCluster.linsert("mykey4", BinaryClient.LIST_POSITION.BEFORE, "HACKER", "OK");
        System.out.println(linsert1); // pivot 不存在， 返回 -1

        Long linsert2 = jedisCluster.linsert("mykey4", BinaryClient.LIST_POSITION.AFTER, "Hello", "Hacker");
        System.out.println(linsert2); // 3, 添加成功，返回列表的长度

    }
}
