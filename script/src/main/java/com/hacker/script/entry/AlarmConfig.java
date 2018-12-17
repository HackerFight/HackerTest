package com.hacker.script.entry;

import java.io.Serializable;

/**
 * @author Hacker
 * @date：2018/12/13
 * @project energyManagement-parent
 * @describe
 */
public class AlarmConfig implements Serializable {

    /**
     * 日配额告警实例的Id
     */
    private String dailyAlarmInstanceId;

    /**
     * 日配额预警实例的Id
     */
    private String dailyWarnInstanceId;

    /**
     * 月配额告警实例的Id
     */
    private String monthAlarmInstanceId;

    /**
     * 月配额预警实例的Id
     */
    private String monthWarnInstanceId;

    public String getDailyAlarmInstanceId() {
        return dailyAlarmInstanceId;
    }

    public void setDailyAlarmInstanceId(String dailyAlarmInstanceId) {
        this.dailyAlarmInstanceId = dailyAlarmInstanceId;
    }

    public String getDailyWarnInstanceId() {
        return dailyWarnInstanceId;
    }

    public void setDailyWarnInstanceId(String dailyWarnInstanceId) {
        this.dailyWarnInstanceId = dailyWarnInstanceId;
    }

    public String getMonthAlarmInstanceId() {
        return monthAlarmInstanceId;
    }

    public void setMonthAlarmInstanceId(String monthAlarmInstanceId) {
        this.monthAlarmInstanceId = monthAlarmInstanceId;
    }

    public String getMonthWarnInstanceId() {
        return monthWarnInstanceId;
    }

    public void setMonthWarnInstanceId(String monthWarnInstanceId) {
        this.monthWarnInstanceId = monthWarnInstanceId;
    }

    @Override
    public String toString() {
        return "AlarmConfig{" +
                "dailyAlarmInstanceId='" + dailyAlarmInstanceId + '\'' +
                ", dailyWarnInstanceId='" + dailyWarnInstanceId + '\'' +
                ", monthAlarmInstanceId='" + monthAlarmInstanceId + '\'' +
                ", monthWarnInstanceId='" + monthWarnInstanceId + '\'' +
                '}';
    }
}
