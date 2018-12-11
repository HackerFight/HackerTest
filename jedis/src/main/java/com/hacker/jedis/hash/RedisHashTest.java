package com.hacker.jedis.hash;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hacker.jedis.conn.JedisConnectionUtils;
import com.hacker.jedis.constants.FieldConstants;
import com.hacker.jedis.entrty.Car;
import com.hacker.jedis.entrty.Person;
import com.hacker.jedis.utils.MapUtils;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe  适合保存对象
 */
public class RedisHashTest {

    private JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();

    @Test
    public void testHash1() {
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * <pre>
         * HSET key field value
            将哈希表 key 中的域 field 的值设为 value 。
            如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
             如果域 field 已经存在于哈希表中，旧值将被覆盖。
         </pre>
         */
        jedisCluster.hset("person", "name", "zhangsan");
        jedisCluster.hset("person", "age", "23");
        jedisCluster.hset("person", "phone", "18368116334");
        jedisCluster.hset("person", "phone", "15853291688");

        /**
         * hget
         */
        String hget = jedisCluster.hget("person", "name");
        System.out.println(hget);
        /**
         * hgetall
         */
        Map<String, String> person = jedisCluster.hgetAll("person");
        System.out.println(person);

        /**
         * HMSET key field value [field value ...]
         *   同时将多个 field-value (域-值)对设置到哈希表 key 中。
         *   这个在集群中使用是没有问题的， 因为他是一个key
         *   在jedis 中他将 多个 field-value 封装在了  map 中
         *   hmset 要比 hset 还一点，个人感觉
         */
        Map<String, String> map = new HashMap<>();
        map.put("name", "lisi");
        map.put("age", "24");
        map.put("phone", "10086");
        jedisCluster.hmset("customer", map);

        /**
         * hmget
         */
        List<String> hmget = jedisCluster.hmget("customer", "name", "age");
        System.out.println(hmget);

        /**
         * hdel: 删除某一个key 的 field 对应的value （将这个key 下的 field-value 移除掉)
         */
        Long hdel = jedisCluster.hdel("customer", "name", "age");
        System.out.println(hdel);  //2   移除了2对 field-value
        System.out.println(jedisCluster.hgetAll("customer")); //{phone=10086}

    }
    
    @Test
    public void testHash2(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        Map<String, String> map = new HashMap<>();
        map.put("A", "123");
        map.put("B", "456");
        map.put("C", "789");
        jedisCluster.hmset("h1", map);

        /**
         * hlen: 返回 key 中 域(field) 的数量
         */
        System.out.println(jedisCluster.hlen("h1"));  //3

        /**
         * HEXISTS key field
         *  查看哈希表 key 中，给定域 field 是否存在
         */
        Boolean hexists = jedisCluster.hexists("h1", "D"); //false
        System.out.println(hexists);
        Boolean hexists2 = jedisCluster.hexists("h1", "C"); //true
        System.out.println(hexists2);

        /**
         * hkeys / hvals
         *   HKEYS key
         *     返回哈希表 key 中的所有域。
         *  HVALS key
         *     返回哈希表 key 中所有域的值。
         */

        Set<String> field = jedisCluster.hkeys("h1");
        System.out.println("所有的field：" + field);
        List<String> hvalue = jedisCluster.hvals("h1");
        System.out.println("所有的field 对应的value: " + hvalue);
    }

    /**
     *  保存对象1
     */
    @Test
    public void testHash3(){
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        /**
         * 直接保存一个 java 对象
         *   在目前练习中是无法直接将一个 java 对象 保存到 redis 中的，尽管 redis 的 hash 表
         *   适合存储对象，但是仍然无法直接保存对象
         *
         *   在测试的时候发现，jedis 中的所有方法都会有一个 byte[] 类型的 重载方法 存在，这说明
         *   我们可以将 java 对象 进行序列化和反序列化来进行存储就可以了
         *
         *   根据个人喜好：可以选择两种方法来保存对象
         *     1）序列化成字节数组 （这个暂时先不测试了)
         *     2) 使用 hash 表（个人喜欢这个，因为 hash 本身是适合存储对象的)
         */

        Person person = new Person("Lisi", 34, "15853991688");
        //提供一个工具类，将javaBean 专程 Map
        Map<String, String> map = MapUtils.conversionMap(person);
        String person1 = jedisCluster.hmset("person", map);
        System.out.println(person1); //OK

        //获取值
        Map<String, String> map1 = jedisCluster.hgetAll("person");
        System.out.println(map1); // {name=Lisi, phone=15853291688, age=34}v
        System.out.println(jedisCluster.hget("person", "name"));

        //还可以一个一个属性设置，这种方式比较繁琐，属性多了就很难受了
        jedisCluster.hset("person1", FieldConstants.NAME, "ZhangSan");
        jedisCluster.hset("person1", FieldConstants.AGE, "28");
        jedisCluster.hset("person1", FieldConstants.PHONE, "15853691688");

        //获取值
        Map<String, String> map2 = jedisCluster.hgetAll("person1"); //一次性获取所有
        System.out.println(map2);
        System.out.println(jedisCluster.hget("person1", FieldConstants.PHONE));

    }

    /**
     *  保存对象2
     */
    @Test
    public void testHash4(){
        /**
         * 序列化成字节数组进行存储，但是我个人十分不愿意用
         * 查阅了一下，网上说可以使用 JSON 字符串来存储
         */
        Car car = new Car("Audi", "300000");
        String car1 = jedisCluster.set("car", JSON.toJSONString(car));
        System.out.println(car1);

        //保存多个对象(前提是这个多个对象用一个key）
        List<Car> carList = new ArrayList<>();
        Car cars1 = new Car("Audi", "300000");
        Car cars2 = new Car("Byd", "100000");
        Car cars3 = new Car("Bmw", "500000");
        carList.add(cars1);
        carList.add(cars2);
        carList.add(cars3);

        //JSONArray.toJSONBytes(carList, SerializerFeature.NotWriteDefaultValue);
        String car2 = jedisCluster.set("car2", JSON.toJSONString(carList));
        System.out.println(car2);
        //[{"brand":"Audi","price":"300000"},{"brand":"Byd","price":"100000"},{"brand":"Bmw","price":"500000"}]
        String result = jedisCluster.get("car2");
        System.out.println(result);

        //转换
        List<Car> cars = JSONArray.parseArray(result).toJavaList(Car.class);
        System.out.println(cars);
    }

    /**
     * Tips: 对于直接存储对象，个人喜欢使用 hmset() 和 set
     *  hmset() 我喜欢把对象转成map，而且 hash 本身也是非常适合存对象
     *  set() 就使用 JSON 转换 来保存对象
     */

}
