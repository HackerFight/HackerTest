package com.hacker.demo;

import java.util.Map;

/**
 * @author Hacker
 * @date：2018/10/11
 * @project project
 * @describe
 */
public interface StationService {

    Map<String, String> queryInverterInfoByStationId(String stationId);
}
