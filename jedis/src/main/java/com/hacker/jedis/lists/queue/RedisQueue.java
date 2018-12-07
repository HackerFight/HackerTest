package com.hacker.jedis.lists.queue;

import com.hacker.jedis.conn.JedisConnectionUtils;
import com.hacker.jedis.entrty.Config;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe
 */
public class RedisQueue {

    private static final List<Config> dataList = new ArrayList<>(); //(int)Math.ceil(size/0.7)
    //线程池
    private static ExecutorService task = Executors.newCachedThreadPool();

    static {
        dataList.add(new Config("1101", "alarm_3.2", true, "图网001"));
        dataList.add(new Config("1102", "alarm_3.2", false, "图网002"));
        dataList.add(new Config("1103", "alarm_3.2", false, "暗洞001"));
        dataList.add(new Config("1104", "alarm_3.2", true, "图网003"));
        dataList.add(new Config("1105", "alarm_3.2", true, "图网004"));
        dataList.add(new Config("1106", "alarm_3.2", true, "暗洞002"));
        dataList.add(new Config("1107", "alarm_3.2", false, "图网006"));
        dataList.add(new Config("1108", "alarm_3.2", true, "暗洞003"));
        dataList.add(new Config("1109", "alarm_3.2", false, "暗洞004"));
        dataList.add(new Config("1110", "alarm_3.2", false, "暗洞005"));
        dataList.add(new Config("1111", "alarm_3.2", false, "图网007"));
        dataList.add(new Config("1112", "alarm_3.2", true, "图网008"));
        dataList.add(new Config("1113", "alarm_3.2", true, "图网009"));
        dataList.add(new Config("1114", "alarm_3.2", true, "暗洞006"));
        dataList.add(new Config("1115", "alarm_3.2", false, "图网010"));
        dataList.add(new Config("1116", "alarm_3.2", true, "暗洞007"));
        dataList.add(new Config("1117", "alarm_3.2", false, "图网011"));
        dataList.add(new Config("1118", "alarm_3.2", true, "暗洞008"));
        dataList.add(new Config("1119", "alarm_3.2", false, "图网012"));
        dataList.add(new Config("1120", "alarm_3.2", true, "暗洞009"));
    }

    public static List<Config> getDataList() {
        return dataList;
    }

    public static Config getData(String stationId) {
        return dataList.stream().filter(data -> stationId.equals(data.getStationId())).limit(1).collect(Collectors.toList()).get(0);
    }

    public static List<String> getIds() {
        return dataList.stream().map(Config::getStationId).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        //获取redis集群
        JedisCluster jedisCluster = JedisConnectionUtils.getJedisCluster();
        task.submit(() -> {
            for (int i = 0; i < dataList.size(); i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Config config = dataList.get(i);
                //如果是有效的，那么就将站点id保存进队列中
                if (config.getEffective()) {
                    //这边往里放
                    jedisCluster.lpush("hacker_queue_test", config.getStationId());
                }
            }
            System.out.println("将数据全部放入了队列中----> OK");
        });

        if (!task.isShutdown()) {
            task.shutdown();
        }
    }
}
