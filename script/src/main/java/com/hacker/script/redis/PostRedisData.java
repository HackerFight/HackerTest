package com.hacker.script.redis;

import com.alibaba.fastjson.JSONObject;
import com.hacker.script.cache.JedisCache;
import com.hacker.script.entry.AlarmConfig;
import com.ioe.alarm.client.rule.service.RuleInstanceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.JedisCluster;

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
        //deleteRuleInstanceIds();
        //restructureAlarmInstance();
    }

    public void specialTreatmentAlarmInstanceIds() {
        List<String> ids = Arrays.asList("30018c70e3701805", "30018c70e3701804", "30028c70e3701801", "30018c70e3701803", "30018c70e3701802", "30018c70e3701801", "30028c70e3701800", "30018c70e3701800");
        System.out.println(ids);
        ruleInstanceService.disableRuleInstanceByRuleInstanceIdList(ids);
    }

    public void test() {
        String s = jedisCluster.get("62118c70c3542f20").replaceAll("'", "");
        AlarmConfig alarmConfig = JSONObject.parseObject(s, AlarmConfig.class);
        System.out.println(alarmConfig);
    }

    public static void main(String[] args) {
        PostRedisData postRedisData = new PostRedisData();
        postRedisData.specialTreatmentAlarmInstanceIds();
    }

}
