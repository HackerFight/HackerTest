package com.hacker.script.entry;

import java.util.List;

/**
 * @author Hacker
 * @date：2018/12/10
 * @project project
 * @describe
 */
public class AlarmConfig {

    private String stationId;

    private String monitoredId;

    //污染设备
    private List<String> deviceIds;

    //治理设备
    private List<NormalConditionDto> normalConditions;

    private Integer alarmContinueTime;

    private Double maxPower;

    Integer businessType;

    String alarmInstanceId;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getMonitoredId() {
        return monitoredId;
    }

    public void setMonitoredId(String monitoredId) {
        this.monitoredId = monitoredId;
    }


    public Integer getAlarmContinueTime() {
        return alarmContinueTime;
    }

    public void setAlarmContinueTime(Integer alarmContinueTime) {
        this.alarmContinueTime = alarmContinueTime;
    }

    public AlarmConfig() {
    }

    public Double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Double maxPower) {
        this.maxPower = maxPower;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public List<NormalConditionDto> getNormalConditions() {
        return normalConditions;
    }

    public void setNormalConditions(List<NormalConditionDto> normalConditions) {
        this.normalConditions = normalConditions;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getAlarmInstanceId() {
        return alarmInstanceId;
    }

    public void setAlarmInstanceId(String alarmInstanceId) {
        this.alarmInstanceId = alarmInstanceId;
    }

    @Override
    public String toString() {
        return "AlarmConfig{" +
                "stationId='" + stationId + '\'' +
                ", monitoredId='" + monitoredId + '\'' +
                ", deviceIds=" + deviceIds +
                ", normalConditions=" + normalConditions +
                ", alarmContinueTime=" + alarmContinueTime +
                ", maxPower=" + maxPower +
                '}';
    }

}
