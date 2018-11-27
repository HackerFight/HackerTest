package com.hacker.demo.entry;

/**
 * @author Hacker
 * @date：2018/11/27
 * @project project
 * @describe
 */
public class PushConfig {

    private String stationId;

    private String stationName;

    private String monitoredObjectId;

    private Long happenTime;

    private Long latestHappenTime;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getMonitoredObjectId() {
        return monitoredObjectId;
    }

    public void setMonitoredObjectId(String monitoredObjectId) {
        this.monitoredObjectId = monitoredObjectId;
    }

    public Long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Long happenTime) {
        this.happenTime = happenTime;
    }

    public Long getLatestHappenTime() {
        return latestHappenTime;
    }

    public void setLatestHappenTime(Long latestHappenTime) {
        this.latestHappenTime = latestHappenTime;
    }
}
