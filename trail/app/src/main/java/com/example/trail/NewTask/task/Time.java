package com.example.trail.NewTask.task;

import lombok.Data;

@Data
public class Time {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
