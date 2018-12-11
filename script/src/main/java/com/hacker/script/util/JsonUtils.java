package com.hacker.script.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/12/10
 * @project project
 * @describe
 */
public class JsonUtils {

    public static <T> List<T> getListByJson(String json, Class<T> tClass) {

        List<T> list = new ArrayList<>();
        if (json.charAt(0) == '[') {
            list = JSONArray.parseArray(json, tClass);
        } else {
            T rule = JSON.parseObject(json, tClass);
            list.add(rule);
        }

        return list;
    }
}
