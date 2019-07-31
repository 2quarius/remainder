package com.example.trail.Course;

import com.example.trail.Http.HttpCourse;
import com.example.trail.NewTask.SimpleTask.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule {
    private String code;
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
    public List<lesson> lessons;

    public Schedule(){
        this.lessons.clear();
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }

    public List<Task> convert(int term){
        HttpCourse httpcourse=new HttpCourse();
        try {
            httpcourse.run_get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lessons=httpcourse.getGetSchedule().lessons;
        List<Task> tasks=new ArrayList<>();
        Date before=new Date();
        if(term==1) {
            String str="2018-09-10 00:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                before=sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            String str="2019-02-25 00:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                before=sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (int i=0;i<this.lessons.size();i++) {
            int size=this.lessons.get(i).getClasses().size();
            for (int j = 0; j <size; j++) {
                Task t = new Task();
                t.setTitle(this.lessons.get(i).getName());
                int addDay=this.lessons.get(i).getClasses().get(j).getDay()+this.lessons.get(i).getClasses().get(j).getWeek()*7;
                Date tmp = addDayOfDate(before,addDay);//增加天数
                tmp=addPeriod(tmp,this.lessons.get(i).getClasses().get(j).getPeriod());
                t.setStartTime(tmp);
                //add class length set
                if (this.lessons.get(i).getClasses().get(j).getPeriod()=="5"&&this.lessons.get(i).getClasses().get(j).getLast()==3){
                    t.setExpireTime(addMinOfDate(tmp,165));
                } else if (this.lessons.get(i).getClasses().get(j).getLast() == 2) {
                    t.setExpireTime(addMinOfDate(tmp,100));
                }else if (this.lessons.get(i).getClasses().get(j).getLast() == 4){
                    t.setExpireTime(addMinOfDate(tmp,220));
                }
                tasks.add(t);
            }
        }
        return tasks;
    }

    public static Date addDayOfDate(Date date,int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        Date newDate = c.getTime();
        return newDate;
    }
    public static Date addHourOfDate(Date date,int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, i);
        Date newDate = c.getTime();
        return newDate;
    }
    public static Date addMinOfDate(Date date,int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, i);
        Date newDate = c.getTime();
        return newDate;
    }
    public Date addPeriod(Date date,String p){
        switch (Integer.parseInt(p)) {
            case 0:
            case 2:
            case 6:
            case 8:
            case 10:date = addHourOfDate(date, 8+Integer.parseInt(p));break;
            case 5:date=addHourOfDate(date,12);date=addMinOfDate(date,55);break;
            default:
        }
        return date;
    }
}
