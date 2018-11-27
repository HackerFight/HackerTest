package com.hacker.demo;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Hacker
 * @date：2018/11/1
 * @project project
 * @describe
 */
public class LocalDateTimeTest {

    @Test
    public void test() {
        Long start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                .with(TemporalAdjusters.firstDayOfMonth())
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(start);
    }

    private static final Integer DAY_MILL = 24 * 60 * 60 * 1000;

    public static void main(String[] args) {

//        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
//        long start = today_start.toInstant(ZoneOffset.ofHours(8)).toEpochMilli(); //今日开始时间
//
//        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
//        long end = today_end.toInstant(ZoneOffset.ofHours(8)).toEpochMilli(); //今日结束时间
//
        //这种方式很够，每个月1号都有问题，因为变成了0,..
//        int year = today_start.getYear();
//        Month month = today_start.getMonth();
//        int day = today_start.getDayOfMonth() - 1;
//        LocalDateTime yesterday_start = LocalDateTime.of(year, month, day, 0, 0, 0);
//        long yesterday_start_time = yesterday_start.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
//
//        LocalDateTime yesterday_end = LocalDateTime.of(today_start.getYear(), today_start.getMonth(), today_start
//                .getDayOfMonth() - 1, 23, 59, 59);
//        long yesterday_end_time = yesterday_end.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
//
//        System.out.println("start " + start);
//        System.out.println("end " + end);
//        System.out.println("yesterday_start_time " + yesterday_start_time);
//        System.out.println("yesterday_end_time " + yesterday_end_time);


        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        long start = today_start.toInstant(ZoneOffset.ofHours(8)).toEpochMilli(); //今日开始时间

        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        long end = today_end.toInstant(ZoneOffset.ofHours(8)).toEpochMilli(); //今日结束时间

        //这是我认为获取前一天开始和结束的最好方法
        long _start = start - DAY_MILL;
        long _end = end - DAY_MILL;
        System.out.println(_start + " --- " + _end);

    }
}
