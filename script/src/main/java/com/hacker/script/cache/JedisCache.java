package com.hacker.script.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hacker
 * @date：2018/12/14
 * @project project
 * @describe
 */
@Component
public class JedisCache implements InitializingBean {

    @Value("${redisClusterUris}")
    private String redisUrl;

    private JedisCluster jedisCluster;

    private final Object lock = new Object();

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initRedisConfig();
    }

    private void initRedisConfig() {
        try {
            synchronized (lock) {
                if (ObjectUtils.isEmpty(jedisCluster)) {
                    Set<HostAndPort> configs = new HashSet<>();
                    String[] hostAndPorts = redisUrl.trim().split(",");
                    for (int i = 0; i < hostAndPorts.length; i++) {
                        String[] hostAndPort = hostAndPorts[i].split(":");
                        configs.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
                    }
                    jedisCluster = new JedisCluster(configs);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
