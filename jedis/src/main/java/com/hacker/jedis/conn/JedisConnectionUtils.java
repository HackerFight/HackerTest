package com.hacker.jedis.conn;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Hacker
 * @date：2018/11/30
 * @project project
 * @describe
 */
public class JedisConnectionUtils {
    //redis 集群
    private static JedisCluster jedisCluster;
    //集群地址
    private static String redisClusterUris;

    private static void init() {
        try {
            Properties properties = new Properties();
            InputStream is = JedisConnectionUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
            properties.load(is);

            redisClusterUris = (String) properties.get("redisClusterUris");

            if (jedisCluster == null) {
                Set<HostAndPort> configs = new HashSet<>();
                String[] hostAndPorts = redisClusterUris.trim().split(",");
                for (int i = 0; i < hostAndPorts.length; i++) {
                    String[] hostAndPort = hostAndPorts[i].split(":");
                    configs.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
                }
                jedisCluster = new JedisCluster(configs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JedisCluster getJedisCluster() {
        init();
        return jedisCluster;
    }

    public static Set<String> getAll(String pattern) {
        init();
        Set<String> keys = new HashSet<>();
        //key 是ip和端口组成的字符串，value 是池
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        Set<String> keySet = clusterNodes.keySet();
        for (String hostAndPort : keySet) {
            System.out.println("所有的主机:" + hostAndPort);
            JedisPool jedisPool = clusterNodes.get(hostAndPort);
            Jedis jedis = jedisPool.getResource();
            try {
                //单机中keys的方法，集群中没有，因为redis存储他是分槽的
                Set<String> key = jedis.keys(pattern);
                keys.addAll(key);
            } catch (Exception e) {
               e.printStackTrace();
            } finally {
                //关闭
                jedis.close();
            }
        }
        return keys;
    }
}
