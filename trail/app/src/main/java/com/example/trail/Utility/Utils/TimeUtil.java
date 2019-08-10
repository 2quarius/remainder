package com.example.trail.Utility.Utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Calendar Date haibin.Calendar 转换
 * ------------------------------------
 * haibin.Calendar:
 * https://github.com/huanghaibin-dev/CalendarView/blob/master/calendarview/src/main/java/com/haibin/calendarview/Calendar.java
 * ------------------------------------
 * TODO 最好全部转为Calendar
 */
public class TimeUtil {
    /**
     * date to calendar
     * @param date
     * @return
     */
    public static Calendar Date2Cal(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * haibin.calendar to date
     * @param calendar
     * @return
     * @throws ParseException
     */
    public static Date Calendar2Date(com.haibin.calendarview.Calendar calendar) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stringBuilder = String.valueOf(calendar.getYear()) +
                "-" +
                calendar.getMonth() +
                "-" +
                calendar.getDay();
        return sdf.parse(stringBuilder);
    }

    /**
     * haibin.calendar to calendar
     * @param calendar
     * @return
     */
    public static Calendar Calendar2Cal(com.haibin.calendarview.Calendar calendar){
        Calendar cal = Calendar.getInstance();
        cal.set(calendar.getYear(),calendar.getMonth(),calendar.getDay());
        return cal;
    }

    /**
     * 取得当前日期所在周的第一天
     * @param calendar
     * @return
     */
    public static Date getFirstDayOfWeek(com.haibin.calendarview.Calendar calendar) throws ParseException {
        //1-31日
        int day = calendar.getDay();
        //0-6：周日-周六
        int week = calendar.getWeek();
        if (week==0){
            week = 7;
        }
        //所在周的起始日
        int begin = day-week<0?1:day-week+1;

        com.haibin.calendarview.Calendar cal = calendar;
        cal.setDay(begin);
        return Calendar2Date(cal);
    }

    /**
     * 取得当前日期下一周的第一天
     * @param calendar
     * @return
     */
    public static Date getFirstDayOfNextWeek(com.haibin.calendarview.Calendar calendar) throws ParseException {
        //1-31日
        int day = calendar.getDay();
        //0-6：周日-周六
        int week = calendar.getWeek();
        if (week==0){
            week = 7;
        }
        //下一周起始日
        int end = day+7-week>getDaysOfMonth(calendar)?getDaysOfMonth(calendar):day+7-week+1;

        com.haibin.calendarview.Calendar cal = calendar;
        cal.setDay(end);
        return Calendar2Date(cal);
    }

    private static int getDaysOfMonth(com.haibin.calendarview.Calendar calendar) {
        Calendar cal = Calendar2Cal(calendar);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
