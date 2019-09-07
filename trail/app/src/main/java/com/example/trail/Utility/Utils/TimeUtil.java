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
    public static com.haibin.calendarview.Calendar Date2Calendar(Date date){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        com.haibin.calendarview.Calendar cal = new com.haibin.calendarview.Calendar();
        cal.setDay(calendar.get(java.util.Calendar.DAY_OF_MONTH));
        cal.setMonth(calendar.get(java.util.Calendar.MONTH));
        cal.setYear(calendar.get(java.util.Calendar.YEAR));
        return cal;
    }
    /**
     * date to calendar
     * @param date
     * @return
     */
    public static Calendar Date2Cal(Date date){
        if (date==null){
            return null;
        }
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

    public static Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
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

    public static Date String2Date(Date expireTime, String text) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expireTime);
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<text.length();i++){
            if (text.charAt(i)==':'){
                break;
            }
            sb.append(text.charAt(i));
        }
        calendar.set(Calendar.HOUR,Integer.valueOf(sb.toString()));
        return calendar.getTime();
    }
}
