package com.example.trail.EventsObject;

import lombok.Data;

//月份事务
@Data
public class MonthEvent {


    //每日事务组成每月事务，以数组形式存储
    private DayEvents[] monthEvents = new DayEvents[31];

    //获取每日第一个事务
    public Event getDayFirstEvent(int day){
        return monthEvents[day].getFirstEvent();
    }

    //获取每日最后一个事务
    public Event getDayLastEvent(int day){
        return monthEvents[day].getLastEvent();
    }
}
