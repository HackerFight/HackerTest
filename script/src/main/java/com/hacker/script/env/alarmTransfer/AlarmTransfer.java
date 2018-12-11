package com.hacker.script.env.alarmTransfer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hacker.script.constants.Constants;
import com.hacker.script.entry.AlarmConfig;
import com.hacker.script.entry.NormalConditionDto;
import com.ioe.alarm.client.rule.entity.RuleInstance;
import com.ioe.alarm.client.rule.service.RuleInstanceService;
import com.ioe.assetManagement.assetaccount.entity.Station;
import com.ioe.assetManagement.assetaccount.service.StationService;
import com.ioe.common.domain.ListResult;
import com.ioe.common.domain.Result;
import com.ioe.metric.client.entity.MonitoredObjectPoint;
import com.ioe.metric.client.service.MonitoredObjectPointService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hacker
 * @date：2018/12/10
 * @project project
 * @describe 环保数据迁移，告警的数据不对，需要重新生成告警实例
 */
public class AlarmTransfer {

    private JdbcTemplate jdbcTemplate;

    private RuleInstanceService ruleInstanceService;

    private MonitoredObjectPointService monitoredObjectPointService;

    private StationService stationService;

    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        ruleInstanceService = ctx.getBean(RuleInstanceService.class);
        monitoredObjectPointService = ctx.getBean(MonitoredObjectPointService.class);
        stationService = ctx.getBean(StationService.class);
    }


    /**
     * 删除数据
     */
    private void deleteAlarmData() {
        try {
            List<String> ids = needDeleteRuleInstanceIds();
            ruleInstanceService.disableRuleInstanceByRuleInstanceIdList(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询环保的告警实例（本地存储的告警相关信息，并不是真正意义上的告警实例)
     */
    private void queryEnvAlarmData() {
        try {
            String sql = "SELECT * FROM t_ep_alarm_object WHERE sys_deleted = 0";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
            System.out.println("需要处理的数据  --> " + maps);
            postAlarmData(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理数据（需要组装)
     *
     * @param maps
     */
    private void postAlarmData(List<Map<String, Object>> maps) {
        try {
            if (!CollectionUtils.isEmpty(maps)) {
                List<AlarmConfig> alarmConfigs = new ArrayList<>();
                for (Map<String, Object> map : maps) {
                    AlarmConfig config = new AlarmConfig();
                    //被监控对象的id
                    String monitoredObjectId = String.valueOf(map.get("monitored_object_id"));
                    //站点id
                    String companyId = String.valueOf(map.get("company_id"));
                    ListResult<Station> stationResult = stationService.queryStationByEnterpriseId(companyId);
                    System.out.println("站点id: -> " + stationResult.getDataList());
                    if (!StringUtils.isEmpty(stationResult.getCode())) {
                        System.out.println("根据企业id查询站点id发生错误: " + companyId);
                        continue;
                    }

                    if (!CollectionUtils.isEmpty(stationResult.getDataList())) {
                        //得到站点id
                        String stationId = stationResult.getDataList().get(0).getStationId();

                        String deviceIds = String.valueOf(map.get("device_ids"));
                        //污染设备
                        List<String> ids = Arrays.asList(deviceIds.split(","));

                        //治理设备
                        String optimizeRule = String.valueOf(map.get("optimize_rule_str"));
                        List<NormalConditionDto> normalConditions = JSONArray.parseArray(optimizeRule).toJavaList(NormalConditionDto.class);

                        //持续时间
                        Integer continueTime = Integer.valueOf(String.valueOf(map.get("alarm_continue_time")));

                        //最大功率
                        Double maxPower = Double.valueOf(String.valueOf(map.get("max_power")));

                        //告警实例的id
                        String alarmInstanceId = String.valueOf(map.get("alarm_instance_id"));
                        System.out.println("规则实例id: " + alarmInstanceId);

                        //保存
                        config.setStationId(stationId);
                        config.setMonitoredId(monitoredObjectId);
                        config.setDeviceIds(ids);
                        config.setNormalConditions(normalConditions);
                        config.setAlarmContinueTime(continueTime);
                        config.setMaxPower(maxPower);
                        config.setBusinessType(1);
                        config.setAlarmInstanceId(alarmInstanceId); //根据他去更新
                        alarmConfigs.add(config);
                    }else {
                        System.out.println("根据企业id [ " + companyId +" ] 查询站点为空; stationService：" + stationService);
                    }
                }
                //构建告警实例
                buildAlarmInstance(alarmConfigs);
            }
        } catch (NumberFormatException e) {
            System.out.println("analyize env alarm data error; " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 构建告警实例
     *
     * @param alarmConfigs
     */
    private void buildAlarmInstance(List<AlarmConfig> alarmConfigs) {
        System.out.println("------------   构建告警实例开始....  -----------------------");
        try {
            if (!CollectionUtils.isEmpty(alarmConfigs)) {
                for (AlarmConfig alarmConfig : alarmConfigs) {
                    StringBuilder equation = new StringBuilder();
                    StringBuilder equationDesc = new StringBuilder();
                    List<String> deviceIds = alarmConfig.getDeviceIds(); //污染设备
                    List<NormalConditionDto> normalConditionDtos = alarmConfig.getNormalConditions(); //治理设备
                    Double maxPower = alarmConfig.getMaxPower(); //最大功率

                    //构建公式
                    buildEquation(deviceIds, normalConditionDtos, equation, equationDesc, maxPower);

                    if (!StringUtils.isEmpty(equation.toString())) {
                        RuleInstance instance = new RuleInstance();
                        instance.setStationId(alarmConfig.getStationId());
                        instance.setDeviceId(alarmConfig.getMonitoredId());
                        instance.setEquation(equation.toString());
                        instance.setEquationDesc(equationDesc.toString());
                        instance.setEvaluateUnit(1);
                        instance.setEvaluateValue(60 * alarmConfig.getAlarmContinueTime());
                        instance.setRuleInstanceId(alarmConfig.getAlarmInstanceId());
                        instance.setBusinessType(Byte.valueOf("1")); //环保的 businessType = 1
                        Result ruleInstance = ruleInstanceService.updateRuleInstanceByInstanceId(JSON.toJSONString(instance));
                        if (!StringUtils.isEmpty(ruleInstance.getCode())) {
                            System.out.println("create alarm instance error;" + alarmConfig + " equation: " + equation.toString());
                        }
                    }
                }
            }
            System.out.println("------------   构建告警实例结束..... -----------------------");
        } catch (Exception e) {
            System.out.println("build alarm instance error;  " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  构建公式
     * @param deviceIds
     * @param conditions
     * @param equation
     * @param equationDesc
     * @param maxPower
     */
    private void buildEquation(List<String> deviceIds, List<NormalConditionDto> conditions, StringBuilder equation,
                               StringBuilder equationDesc, Double maxPower) {
        try {
            Set<String> allIds = new HashSet<>(deviceIds);
            conditions.forEach(c -> allIds.addAll(c.getDeviceIds()));
            //查询point
            ListResult<MonitoredObjectPoint> pointsResult = monitoredObjectPointService
                    .queryPointByMonitoredObjectIdsAndMetricCodes(new ArrayList<>(allIds),
                            Collections.singletonList(Constants.METRIC_ITEM_POWER));

            if (!StringUtils.isEmpty(pointsResult.getCode())) {
                System.out.println("with deviceId and metric code query point error");
            }
            
            if (!CollectionUtils.isEmpty(pointsResult.getDataList())) {
                List<MonitoredObjectPoint> points = pointsResult.getDataList();
                Map<String, MonitoredObjectPoint> pointMap = points.stream().collect(
                        Collectors.toMap(MonitoredObjectPoint::getMonitoredObjectId, (p -> p)));

                //污染设备
                equation.append("(");
                deviceIds.forEach(
                        id -> equation.append("#{").append(pointMap.get(id).getPointId()).append("}+"));
                equation.delete(equation.length() - 1, equation.length()).append(") >= ").append(maxPower)
                        .append(" && (");

                //治理设备
                conditions.forEach(c -> {
                    equation.append("(");
                    c.getDeviceIds().forEach(id -> {
                        equation.append("#{").append(pointMap.get(id).getPointId()).append("}+");
                    });
                    equation.delete(equation.length() - 1, equation.length()).append(") < ")
                            .append(c.getMinPower()).append(" || ");
                });
                equation.delete(equation.length() - 3, equation.length()).append(")");

                equationDesc.append("(污染设备总正向有功功率>=").append(maxPower).append(")且(任一治理设备为异常工况)");
            }
        } catch (Exception e) {
            System.out.println("handle equation error; " + e.getMessage());
            e.printStackTrace();
        }
    }


    private List<String> needDeleteRuleInstanceIds() {
       return Arrays.asList("30028c790cc3f3e0",
                "30028c790cc3f3e1",
                "30018c790cc3f3e0",
                "30028c790cc3f3e2",
                "30018c790cc26d40",
                "30018c790cc26d41",
                "30018c790cc26d42",
                "30028c790cc26d40",
                "30018c790cc26d43",
                "30028c790cc26d41",
                "30028c790cc26d42",
                "30028c790cc26d43",
                "30028c790cc26d44",
                "30018c790cc26d44",
                "30028c790cc26d45",
                "30028c790cc26d46",
                "30028c790cc26d47",
                "30028c790cc26d48",
                "30028c790cc26d49",
                "30018c790cc26d45",
                "30018c790cc26d46",
                "30018c790cc26d47",
                "30028c790cc26d4a",
                "30028c790cc26d4b",
                "30018c790cc26d48",
                "30018c790cc26d49",
                "30028c790cc26d4c",
                "30028c790cc26d4d",
                "30028c790cc26d4e",
                "30018c790cc26d4a",
                "30018c790cc26d4b",
                "30018c790cc26d4c",
                "30028c790cc26d4f",
                "30028c790cc26d50",
                "30018c790cc26d4d",
                "30028c790cc26d51",
                "30028c790cc26d52",
                "30018c790cc26d4e",
                "30018c790cc26d4f",
                "30018c790cc26d50",
                "30018c790cc26d51",
                "30028c790cc26d53",
                "30028c790cc26d54",
                "30018c790cc26d52",
                "30018c790cc26d53",
                "30018c790cc26d54",
                "30018c790cc26d55",
                "30018c790cc26d56",
                "30028c790cc26d55",
                "30028c790cc26d56",
                "30028c790cc26d57",
                "30018c790cc26d57",
                "30018c790cc26d58",
                "30028c790cc26d58",
                "30028c790cc26d59",
                "30028c790cc26d5a",
                "30028c790cc26d5b",
                "30028c790cc26d5c",
                "30018c790cc26d59",
                "30028c790cc26d5d",
                "30028c790cc26d5e",
                "30028c790cc26d5f",
                "30028c790cc26d60",
                "30028c790cc26d61",
                "30018c790cc26d5a",
                "30028c790cc26d62",
                "30028c790cc26d63",
                "30018c790cc26d5b",
                "30018c790cc26d5c",
                "30028c790cc26d64",
                "30018c790cc26d5d",
                "30028c790cc26d65",
                "30028c790cc26d66",
                "30018c790cc26d5e",
                "30028c790cc26d67",
                "30028c790cc26d68",
                "30028c790cc26d69",
                "30018c790cc26d5f",
                "30018c790cc26d60",
                "30018c790cc26d61",
                "30018c790cc0e6a0",
                "30018c790cc0e6a1",
                "30018c790cc0e6a2",
                "30018c790cc0e6a3",
                "30018c790cc0e6a4",
                "30018c790cc0e6a5");
    }

    public static void main(String[] args) {
        AlarmTransfer alarmTransfer = new AlarmTransfer();
        alarmTransfer.deleteAlarmData();
        alarmTransfer.queryEnvAlarmData();
    }
}
