package com.hacker.script.redis;

import com.alibaba.fastjson.JSONObject;
import com.hacker.script.cache.JedisCache;
import com.hacker.script.entry.AlarmConfig;
import com.ioe.alarm.client.rule.service.RuleInstanceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/12/14
 * @project project
 * @describe
 */
public class PostRedisData {

    private static final String QUERY_QUOTA = "SELECT * FROM t_energy_quota";

    private static final String QUERY_INSTANCE = "SELECT * FROM t_rule_instance WHERE business_type = 6 AND rule_name like '%能耗%' AND sys_deleted = 0" +
            " and device_id = ? order by rule_name";

    private JdbcTemplate jdbcTemplate;

    private RuleInstanceService ruleInstanceService;

    private JedisCluster jedisCluster;


    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        ruleInstanceService = ctx.getBean(RuleInstanceService.class);
        jedisCluster = ctx.getBean(JedisCache.class).getJedisCluster();

    }

    public void postCacheData() {
        deleteRuleInstanceIds();
        restructureAlarmInstance();
    }

    private void restructureAlarmInstance() {
        String[] datas = datas().split("\n");
        for (int i = 0; i < datas.length; i++) {
            String[] split = datas[i].trim().split(" ");
            jedisCluster.set(split[1], split[2].replaceAll("'", ""));
        }
    }

    private void deleteRuleInstanceIds() {
        try {
            String id = needDeleteRuleInstanceIds();
            String[] split = id.split(",");
            List<String> ids = new ArrayList<>(split.length);
            List<String> list = Arrays.asList(split);
            list.stream().forEach(item -> ids.add(item.trim()));
            ids.add("30018c630b587f03");
            ids.add("30028c630b587f09");
            ids.add("30028c630b587f15");
            ids.add("30018c630b587f15");
            ids.add("30018c7913231e04");
            ids.add("30018c7913231e05");
            ruleInstanceService.disableRuleInstanceByRuleInstanceIdList(ids);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("禁用实例出错 -> "+ e.getMessage());
        }
    }


    public void test() {
        String s = jedisCluster.get("62118c70c3542f20").replaceAll("'", "");
        AlarmConfig alarmConfig = JSONObject.parseObject(s, AlarmConfig.class);
        System.out.println(alarmConfig);
    }

    public static void main(String[] args) {
         new PostRedisData().postCacheData();
    }


    private static String datas() {
      return  "set 62108c790d3af8a1 '{\"dailyAlarmInstanceId\":\"30028c790f8f9700\",\"dailyWarnInstanceId\":\"30018c790f8f9700\",\"monthAlarmInstanceId\":\"30018c790f8f9702\",\"monthWarnInstanceId\":\"30018c790f8f9703\"}'\n" +
              "set 62118c6307b5b5c1 '{\"dailyAlarmInstanceId\":\"30028c630b587f0d\",\"dailyWarnInstanceId\":\"30028c630b587f0f\",\"monthAlarmInstanceId\":\"30018c630b587f09\",\"monthWarnInstanceId\":\"30018c630b587f0b\"}'\n" +
              "        set 62118c70c3542f20 '{\"dailyAlarmInstanceId\":\"30018c70ca676713\",\"dailyWarnInstanceId\":\"30028c70ca676711\",\"monthAlarmInstanceId\":\"30028c70ca676712\",\"monthWarnInstanceId\":\"30028c70ca676714\"}'\n" +
              "        set 62118c790d51dc00 '{\"dailyAlarmInstanceId\":\"30018c790f8f9712\",\"dailyWarnInstanceId\":\"30028c790f8f970f\",\"monthAlarmInstanceId\":\"30018c790f8f9713\",\"monthWarnInstanceId\":\"30028c790f8f9712\"}'\n" +
              "        set 62108c70c3329d60 '{\"dailyAlarmInstanceId\":\"30018c70ca676704\",\"dailyWarnInstanceId\":\"30018c70ca676705\",\"monthAlarmInstanceId\":\"30018c70ca676707\",\"monthWarnInstanceId\":\"30028c70ca676702\"}'\n" +
              "        set 62108c790d5b03c0 '{\"dailyAlarmInstanceId\":\"30018c790f8f970e\",\"dailyWarnInstanceId\":\"30028c790f8f970a\",\"monthAlarmInstanceId\":\"30018c790f8f9710\",\"monthWarnInstanceId\":\"30028c790f8f970d\"}'\n" +
              "        set 62108c63072f6ec1 '{\"dailyAlarmInstanceId\":\"30028c630b587f00\",\"dailyWarnInstanceId\":\"30018c630b587f00\",\"monthAlarmInstanceId\":\"30018c630b587f01\",\"monthWarnInstanceId\":\"30018c630b587f02\"}'\n" +
              "        set 62108c79101d7f20 '{\"dailyAlarmInstanceId\":\"30028c7af5ef4f00\",\"dailyWarnInstanceId\":\"30028c7af5ef4f02\",\"monthAlarmInstanceId\":\"30028c7af5ef4f03\",\"monthWarnInstanceId\":\"30018c7af5ef4f01\"}'\n" +
              "        set 62108c6307c37160 '{\"dailyAlarmInstanceId\":\"30028c630b587f0a\",\"dailyWarnInstanceId\":\"30018c630b587f04\",\"monthAlarmInstanceId\":\"30028c630b587f0c\",\"monthWarnInstanceId\":\"30018c630b587f06\"}'\n" +
              "        set 62108c70901326c0 '{\"dailyAlarmInstanceId\":\"30028c76e1a95300\",\"dailyWarnInstanceId\":\"30018c76e1a95301\",\"monthAlarmInstanceId\":\"30018c76e1a95302\",\"monthWarnInstanceId\":\"30018c76e1a95304\"}'\n" +
              "        set 62108c7898e04a00 '{\"dailyAlarmInstanceId\":\"30028c78a445c509\",\"dailyWarnInstanceId\":\"30028c78a445c50b\",\"monthAlarmInstanceId\":\"30018c78a445c510\",\"monthWarnInstanceId\":\"30018c78a445c512\"}'\n" +
              "        set 62118c790d2ec3a1 '{\"dailyAlarmInstanceId\":\"30028c790f8f9703\",\"dailyWarnInstanceId\":\"30028c790f8f9704\",\"monthAlarmInstanceId\":\"30028c790f8f9706\",\"monthWarnInstanceId\":\"30028c790f8f9707\"}'\n" +
              "        set 62108c7899fc1a40 '{\"dailyAlarmInstanceId\":\"30018c78a445c505\",\"dailyWarnInstanceId\":\"30028c78a445c504\",\"monthAlarmInstanceId\":\"30028c78a445c506\",\"monthWarnInstanceId\":\"30028c78a445c507\"}'\n" +
              "        set 62118c790d45a700 '{\"dailyAlarmInstanceId\":\"30018c790f8f9708\",\"dailyWarnInstanceId\":\"30028c790f8f9708\",\"monthAlarmInstanceId\":\"30018c790f8f9709\",\"monthWarnInstanceId\":\"30018c790f8f970b\"}'\n" +
              "        set 62108c6307973140 '{\"dailyAlarmInstanceId\":\"30018c630b587f0d\",\"dailyWarnInstanceId\":\"30018c630b587f0e\",\"monthAlarmInstanceId\":\"30018c630b587f10\",\"monthWarnInstanceId\":\"30028c630b587f11\"}'\n" +
              "        set 62108c79102399a0 '{\"dailyAlarmInstanceId\":\"30028c7af5ef4f05\",\"dailyWarnInstanceId\":\"30028c7af5ef4f07\",\"monthAlarmInstanceId\":\"30028c7af5ef4f08\",\"monthWarnInstanceId\":\"30018c7af5ef4f04\"}'\n" +
              "        set 62118c70c2fbaee0 '{\"dailyAlarmInstanceId\":\"30018c70ca67670b\",\"dailyWarnInstanceId\":\"30028c70ca67670a\",\"monthAlarmInstanceId\":\"30028c70ca67670c\",\"monthWarnInstanceId\":\"30018c70ca67670c\"}'\n" +
              "        set 62118c789af34b80 '{\"dailyAlarmInstanceId\":\"30028c78a445c50c\",\"dailyWarnInstanceId\":\"30028c78a445c50d\",\"monthAlarmInstanceId\":\"30028c78a445c50f\",\"monthWarnInstanceId\":\"30018c78a445c516\"}'\n" +
              "        set 62108c70a04e1ea0 '{\"dailyAlarmInstanceId\":\"30028c78a445c500\",\"dailyWarnInstanceId\":\"30018c78a445c501\",\"monthAlarmInstanceId\":\"30018c78a445c503\",\"monthWarnInstanceId\":\"30028c78a445c502\"}'\n" +
              "        set 62118c70c38b1da0 '{\"dailyAlarmInstanceId\":\"30018c70ca676717\",\"dailyWarnInstanceId\":\"30018c70ca676718\",\"monthAlarmInstanceId\":\"30018c70ca67671a\",\"monthWarnInstanceId\":\"30018c70ca67671b\"}'\n" +
              "        set 62108c708ef44940 '{\"dailyAlarmInstanceId\":\"30018c78a445c507\",\"dailyWarnInstanceId\":\"30018c78a445c50a\",\"monthAlarmInstanceId\":\"30018c78a445c50b\",\"monthWarnInstanceId\":\"30018c78a445c50d\"}'\n" +
              "        set 62108c708f376cc0 '{\"dailyAlarmInstanceId\":\"30028c78a0b23e00\",\"dailyWarnInstanceId\":\"30028c78a0b23e02\",\"monthAlarmInstanceId\":\"30028c70a3109a00\",\"monthWarnInstanceId\":\"30018c70a3109a00\"}'\n" +
              "        set 62118c790d611e40 '{\"dailyAlarmInstanceId\":\"30018c790f8f9714\",\"dailyWarnInstanceId\":\"30028c790f8f9714\",\"monthAlarmInstanceId\":\"30028c790f8f9716\",\"monthWarnInstanceId\":\"30028c790f8f9717\"}'\n" +
              "        set 62108c70c3743a40 '{\"dailyAlarmInstanceId\":\"30028c70ca676704\",\"dailyWarnInstanceId\":\"30018c70ca676709\",\"monthAlarmInstanceId\":\"30028c70ca676706\",\"monthWarnInstanceId\":\"30028c70ca676707\"}'\n" +
              "        set 62118c70c3204de0 '{\"dailyAlarmInstanceId\":\"30018c70ca67670e\",\"dailyWarnInstanceId\":\"30018c70ca67670f\",\"monthAlarmInstanceId\":\"30028c70ca676710\",\"monthWarnInstanceId\":\"30018c70ca676711\"}'\n" +
              "        set 62108c6307ac8e00 '{\"dailyAlarmInstanceId\":\"30028c630b587f04\",\"dailyWarnInstanceId\":\"30028c630b587f06\",\"monthAlarmInstanceId\":\"30028c630b587f07\",\"monthWarnInstanceId\":\"30028c630b587f08\"}'\n" +
              "        set 62108c6307ac8e00 '{\"dailyAlarmInstanceId\":\"30028c630b587f12\",\"dailyWarnInstanceId\":\"30018c630b587f13\",\"monthAlarmInstanceId\":\"30028c630b587f13\",\"monthWarnInstanceId\":\"30028c630b587f14\"}'\n" +
              "        set 62108c6307ac8e00 '{\"dailyAlarmInstanceId\":\"30028c7913231e04\",\"dailyWarnInstanceId\":\"30018c7913231e08\",\"monthAlarmInstanceId\":\"30018c7913231e06\",\"monthWarnInstanceId\":\"30018c7913231e07\"}'";
    }


    private static String needDeleteRuleInstanceIds() {
        return "30028c630b587f01,30028c630b587f03,30028c630b587f02,30028c630b587f05,30028c630b587f0b,30018c630b587f07,30018c630b587f05,30028c630b587f0e,30018c630b587f08,30018c630b587f0c,30018c630b587f0a,30028c630b587f10,30018c630b587f0f,30018c630b587f12, 30018c630b587f11, 30018c630b587f14, 30028c78a0b23e01, 30018c70a3109a01, 30018c78a0b23e00, 30028c70a3109a01, 30028c70ca676700, 30028c70ca676701, 30018c70ca676706, 30028c70ca676703, 30018c70ca676708, 30018c70ca67670a, 30028c70ca676705, 30028c70ca676708, 30028c70ca676709, 30028c70ca67670d, 30028c70ca67670b, 30018c70ca67670d, 30028c70ca67670e, 30018c70ca676710, 30028c70ca67670f, 30018c70ca676712, 30018c70ca676714, 30028c70ca676713, 30018c70ca676715, 30018c70ca676716, 30028c70ca676715, 30028c70ca676716, 30018c70ca676719, 30018c70ca67671c, 30018c76e1a95300, 30018c76e1a95303, 30028c76e1a95301, 30028c76e1a95302, 30018c78a445c500, 30028c78a445c501, 30018c78a445c502, 30018c78a445c504, 30028c78a445c503, 30018c78a445c506, 30028c78a445c505, 30028c78a445c508, 30018c78a445c508, 30018c78a445c50c, 30018c78a445c509, 30018c78a445c50e, 30028c78a445c50a, 30018c78a445c511, 30018c78a445c50f, 30018c78a445c513, 30018c78a445c514, 30018c78a445c515, 30028c78a445c50e, 30018c78a445c517, 30018c790f8f9701, 30028c790f8f9702, 30028c790f8f9701, 30018c790f8f9704, 30028c790f8f9709, 30018c790f8f970c, 30018c790f8f970a, 30018c790f8f970d, 30018c790f8f970f, 30028c790f8f970c, 30028c790f8f970b, 30018c790f8f9711, 30018c790f8f9715, 30018c790f8f9716, 30028c790f8f9715, 30018c790f8f9717, 30028c790f8f970e, 30028c790f8f9711, 30028c790f8f9710, 30028c790f8f9713, 30018c790f8f9705, 30018c790f8f9706, 30028c790f8f9705, 30018c790f8f9707, 30018c7af5ef4f02, 30028c7af5ef4f09, 30018c7af5ef4f03, 30018c7af5ef4f05, 30028c7af5ef4f01, 30028c7af5ef4f04, 30018c7af5ef4f00, 30028c7af5ef4f06";
    }
}
