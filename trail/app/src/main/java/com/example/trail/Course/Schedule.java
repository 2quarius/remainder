package com.example.trail.Course;

import com.example.trail.NewTask.SimpleTask.Task;

import java.util.List;

public class Schedule {
    public class classes{
        private int week;// 周次（0-17），如5表示第六周
        private  int day;// 星期（0-6），如0表示星期一
        private  String period;// 节次（0-17），如0表示第一节
        private int last; // 持续课节

        public int getDay() {
            return day;
        }

        public int getLast() {
            return last;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public void setLast(int last) {
            this.last = last;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }
    }
    public class lesson{
        private String name;// 课程名
        private String code;// 课程编码
        private List<classes> classes;//一门课的所有上课时间

        public List<Schedule.classes> getClasses() {
            return classes;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public void setClasses(List<Schedule.classes> classes) {
            this.classes = classes;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    List<lesson> lessons;

    public Schedule(){
        this.lessons.clear();

    }
    public List<Task> convert(){
        for (int i=0;i<lessons.size();i++)
            for (int j=0;j<lessons.get(i).classes.size();j++){
                Task t=new Task();
                t.setTitle(lessons.get(i).getName());
                Time tmp=new Time();

                t.setStartTime(tmp);
                tmp.setHour();
                t.setEndTime(tmp);
            }
        return ;
    }

}
