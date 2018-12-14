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

    private static final Integer DAY_MILL = 24 * 60 * 60 * 1000;

    @Test
    public void test() {
        Long start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                .with(TemporalAdjusters.firstDayOfMonth())
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(start);
    }


    /**
     * 获取上个月的开始时间戳
     */
    @Test
    public void testLocalDateTime(){
        //指定到当前时间的零点
        LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        //指定到本月1号零点
        LocalDateTime with = of.with(TemporalAdjusters.firstDayOfMonth());
        //指定到上个月
        LocalDateTime localDateTime = with.minusMonths(1L);
        //调整时差，转换成时间戳
        long lastMonthBegin = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(lastMonthBegin);
    }



    public static void main(String[] args) {

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
