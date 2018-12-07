package com.hacker.jedis.entrty;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe
 */
public class Config {

    private String stationId;

    private String topic;

    private Boolean isEffective;

    private String targetName;


    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getEffective() {
        return isEffective;
    }

    public void setEffective(Boolean effective) {
        isEffective = effective;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "stationId='" + stationId + '\'' +
                ", topic='" + topic + '\'' +
                ", isEffective=" + isEffective +
                ", targetName='" + targetName + '\'' +
                '}';
    }


    public Config(String stationId, String topic, Boolean isEffective, String targetName) {
        this.stationId = stationId;
        this.topic = topic;
        this.isEffective = isEffective;
        this.targetName = targetName;
    }

    public Config() {
    }
}
