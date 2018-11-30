package com.hacker.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hacker.demo.entry.PushConfig;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hacker
 * @date：2018/11/24
 * @project project
 * @describe
 */
public class IntegerTest {

    @Test
    public void testEquals() {
        Integer a = 128;
        Integer b = 128;
        System.out.println(a == b);

        Byte c = 127;
        Byte d = 127;
        System.out.println(c == d);
    }

    @Test
    public void testSubString() {
        String str = "93020016747749385=110.0";
        //String str = "93020054295158793=100.0_93020016747749385=110.0_93020103729225737=120.0";
        BigDecimal sum = BigDecimal.valueOf(0.0);
        String[] datas = str.split("_");
        for (int i = 0; i < datas.length; i++) {
            String[] actual = datas[i].split("=");
            sum = sum.add(new BigDecimal(actual[1]));
        }
        System.out.println(sum);
    }

    @Test
    public void testStream() {
        List<Integer> numbers = Arrays.asList(1, null, 56, 78, null, 23, null);

        numbers.stream().forEach(num -> {
            if (null == num) {
                return;
            }
            System.out.println(num);
        });
    }

    @Test
    public void testPID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        int _pid = Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
        System.out.println("_pid = " + _pid);
    }

    @Test
    public void testSub() {
        String str = "C相电流=100.05878485848441A_B相电流=210.1448415484A_A相电流=210.0A";
        DecimalFormat format = new DecimalFormat("#.00");
        StringBuilder sb = new StringBuilder();
        String[] desc = str.split("_");
        for (int i = 0; i < desc.length; i++) {
            String[] veryData = desc[i].split("=");
            System.out.println(Arrays.toString(veryData));
            System.out.println(veryData[1]);
//            String subData = format.format();
//            sb.append(veryData[0]).append("=").append(subData).append("_");
        }

        System.out.println(sb.toString());
    }


    @Test
    public void testRegExp() {
        String regExp = "^[0-9]+(.[0-9]+)[A-Za-z]*";
        String regExpNumber = "[^a-zA-Z]"; //匹配不包含字符部分
//        String data = "C相电流=100.05878485848441A_B相电流=210.1448415484A_A相电流=210.0A";
        String data = "开关量输入1=跳闸_开关量输入1=0_月正向有功总电量=12000.0kWh";
        List<String> lastHappenValueList = new ArrayList<>();
        String[] split = data.split("_");
        for (int i = 0; i < split.length; i++) {
            String[] items = split[i].split("=");
            String prefix = items[0];
            String suffix = items[1];
            boolean matches = Pattern.matches(regExp, suffix);
            if (matches) {
                Pattern pattern = Pattern.compile(regExpNumber);
                Matcher matcher = pattern.matcher(suffix);
                if (matcher.find()) {
                    //单位
                    String unit = matcher.replaceAll("").trim();
                    //截取单位前的数字
                    String actual = suffix.substring(0, suffix.lastIndexOf(unit));
                    //转换成 BigDecimal 并四舍五入
                    BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(actual));
                    BigDecimal newValue = bigDecimal.setScale(2, RoundingMode.HALF_UP);
                    String last = prefix + "=" + newValue + unit;
                    //目前
                    lastHappenValueList.add(last);
                }
            } else {
                String last = prefix + "=" + suffix;
                lastHappenValueList.add(last);
            }
        }
        //排序
        Collections.sort(lastHappenValueList);
        //规约
        String latestValue = lastHappenValueList.stream().reduce((item1, item2) -> item1 + "_" + item2).get();
        System.out.println(latestValue);
    }

    @Test
    public void testCanvoter() {
        Long lastHappenTime = 1542865392000L;
        Instant instant = Instant.ofEpochMilli(lastHappenTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;  yyyy年MM月dd日 HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(localDateTime);
        System.out.println(format);
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.of(2018, 11, 27, 1, 0, 0);
//        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        String format = formatter.format(now);
        System.out.println(Integer.valueOf(format));
        Integer curentTime = Integer.valueOf(format);

        Integer startTime = 300;
        Integer endTime = 2359;
        if (startTime <= curentTime && curentTime <= endTime) {
            System.out.println("可以推送");
        }
    }

    @Test
    public void testHandle() {
        Integer s = postTime("404");
        System.out.println("s = " + s);
    }

    private Integer postTime(String time) {
        int length = time.length();
        if (length < 4) {
            for (int i = 0; i < 4 - length; i++) {
                time = 0 + time;
            }
        }
        return Integer.valueOf(time);
    }

    @Test
    public void testJson() {
        PushConfig pushConfig = new PushConfig();
        pushConfig.setStationId("1001");
        pushConfig.setStationName("北京");
        pushConfig.setMonitoredObjectId("10058934");
        pushConfig.setHappenTime(152045874000L);

        Map<String, Object> map = new HashMap<>();
        map.put("A", "120111");
        map.put("B", "SEJEIJ0");
        map.put("C", "上海");

        String string = JSON.toJSONString(map);
        System.out.println("string = " + string);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("A", "1000001");
        jsonObject.put("B", "2000002");
        jsonObject.put("C", "3000003");

        String jsonString = JSON.toJSONString(jsonObject);
        System.out.println("'jsonString' = " + jsonString);
        System.out.println(jsonObject);
    }

    @Test
    public void testGetWeek() {
        Integer integer = analyzeWeek();
        System.out.println(integer);
    }

    private Integer analyzeWeek() {
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfWeek().getValue();
    }

    @Test
    public void testGetMonth() {
        LocalDateTime now = LocalDateTime.now();
        int dayOfMonth = now.getDayOfMonth();
        System.out.println(dayOfMonth);
    }

    @Test
    public void testContain() {
        List<String> list = new ArrayList<>();
        list.add("100");
        list.add("200");
        boolean contains = list.contains("100");
        System.out.println(contains);
    }

    @Test
    public void testEquals2() {
        Long a = 456L;
        Long b = 456L;
        System.out.println(a == b);
        System.out.println(a.equals(b));

        System.out.println(Long.compare(0, 0) == 0);
        Long setCache = 1L;
        System.out.println(setCache.equals(0L));

        System.out.println(Long.compare(setCache, 1) == 0);
    }

    @Test
    public void testStringBuilder() {
        JSONObject data = new JSONObject();
        PushConfig pushConfig = new PushConfig();
        data.put("stationName", "站点1");
        data.put("alarmTime", "2018-11-27 14:25:36");
        data.put("infoCategoryName", "告警");
        data.put("monitoredObjectName", "设备1-1");
        data.put("alarmRuleTypeName", "能耗超额");
        data.put("desc", "过电压=15V");

        StringBuilder sb = new StringBuilder();
        String string = sb.append("站点1").append("@").append("2018-11-27 14:25:36").append("@")
                .append("告警").append("@")
                .append("设备1-1").append("@")
                .append("能耗超额").append("@")
                .append("过电压=15V").toString();

        System.out.println(string);

        System.out.println(Arrays.toString(string.split("@")));


    }

    @Test
    public void testForeach() {
        List<PushConfig> list = new ArrayList<>();
        list.add(new PushConfig("1001", "A"));
        list.add(new PushConfig("1002", "B"));
        list.add(new PushConfig("1003", "C"));
        list.add(new PushConfig("1004", "D"));

        list.stream().forEach(pushConfig -> {
            String stationId = pushConfig.getStationId();
            String name = getName(stationId);
            pushConfig.setStationName(name);
        });

        System.out.println(list);
    }

    private String getName(String ID) {
        if ("1004".equals(ID)) {
            return "修改了名字";
        }
        return null;
    }


    @Test
    public void testSplit() {
        String str = "OK%0%5zw18c36485bd320_6";
        String[] split = str.split("%");
        System.out.println(Arrays.toString(split));
        System.out.println(split[0]);
        System.out.println(split[1]);
        System.out.println(split[2]);
    }

    @Test
    public void testEquals3() {
        long a = -36958;
        System.out.println(a <= 0);
    }

    @Test
    public void testEmpty() {
        System.out.println(postEmpty());
    }

    private List<PushConfig> postEmpty() {
        return Collections.emptyList();
    }

    @Test
    public void testEmptyList() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("123");
        System.out.println("list = " + list);

        Set<String> sets = new HashSet<>(list);
        System.out.println("sets = " + sets);
    }
    
    @Test
    public void testSub2(){
       String str = "2400.0<=(#{88226390603730971}) && (#{88226390603730971})<3000.0";

        String substring = str.substring(str.lastIndexOf("<") + 1);
        System.out.println(substring);
    }

}
