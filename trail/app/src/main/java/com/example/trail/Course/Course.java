package com.example.trail.Course;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

public class Course implements ScheduleEnable {
    private String id;
    private String name;
    private String teacher;

    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getTeacher(){return this.teacher;}

    @Override
    public Schedule getSchedule() {
        Schedule schedule=new Schedule();
//        schedule.setDay(getId());
        return schedule;
    }
}
