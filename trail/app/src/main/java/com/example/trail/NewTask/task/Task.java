package com.example.trail.NewTask.task;

import lombok.Data;

@Data
public class Task {
    private Time notifyTime;
    private Time startTime;
    private Time endTime;
    private int recycling; //每日->1 每周->2 每月->3 每年->4
    private int priority;
    private String title;
    private String content;
    private int done = 0;
    public int added = 0;

    public void setTime(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setNotifyTime(Time notifyTime) {
        this.notifyTime = notifyTime;
    }

//    public void setEndTime(Time endTime) {
//        this.endTime = endTime;
//    }
//
//    public void setStartTime(Time startTime) {
//        this.startTime = startTime;
//    }
//
    public void setContent(String content) {
        this.content = content;
    }
//
//    public void setDone(int done) {
//        this.done = done;
//    }
//
//    public void setPriority(int priority) {
//        this.priority = priority;
//    }
//
//    public void setRecycling(int recycling) {
//        this.recycling = recycling;
//    }
//
    public void setTitle(String title) {
        this.title = title;
    }
//
//    public int getDone() {
//        return done;
//    }
//
//    public int getPriority() {
//        return priority;
//    }
//
//    public int getRecycling() {
//        return recycling;
//    }
//
    public String getContent() {
        return content;
    }
//
    public String getTitle() {
        return title;
    }
//
//    public Time getEndTime() {
//        return endTime;
//    }
//
   public String getStartTime() {
       return startTime.getTime();
    }

    public String getNotifyTime() {
        return notifyTime.getTime();
    }
//
}