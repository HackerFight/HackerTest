package com.hacker.script.entry;

import java.util.List;

/**
 * 正常工况，每个告警对象可以关联多个正常工况，任意一个工况异常均触发告警
 *
 * @auther qiys@hzzh.com
 * @date 2018-05-17
 */
public class NormalConditionDto {

    /**
     * 治理设备id,有备用机时可能为多个
     */
    private List<String> deviceIds;

    /**
     * 正常工况有功功率，>= minPower 为正常，< minPower 为异常
     */
    private Double minPower;

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public Double getMinPower() {
        return minPower;
    }

    public void setMinPower(Double minPower) {
        this.minPower = minPower;
    }

    @Override
    public String toString() {
        return "NormalConditionDto{" +
                "deviceIds=" + deviceIds +
                ", minPower=" + minPower +
                '}';
    }
}