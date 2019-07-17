package com.example.trail.NewTask.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MonthTasks {
    private List<DayTasks> monthTasks=new ArrayList<>(31);
    public Task getDayFirstTask(int day){
        return monthTasks.get(day).getFirstTask();
    }

    //获取每日最后一个事务
    public Task getDayLastTask(int day){
        return monthTasks.get(day).getLastTask();
    }
    public  MonthTasks(){
        for(int i=0;i<31;i++){
            DayTasks dayTasks=new DayTasks();
            monthTasks.add(dayTasks);
        }
    }
    public void addTask(int day,Task task){
        monthTasks.get(day).addTask(task);
    }
}
