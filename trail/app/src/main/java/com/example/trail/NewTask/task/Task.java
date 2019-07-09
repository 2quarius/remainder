package com.example.trail.NewTask;

import com.example.trail.NewTask.task.Time;

import lombok.Data;

@Data
public class Task {
    private Time startTime;
    private Time endTime;
    private int recycling; //每日->1 每周->2 每月->3 每年->4
    private int timeAdvance;
    private int priority;
    private String type;
    private String tital;
    private String content;
    private int done;

    public void setTime(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRecycling(int recycling) {
        this.recycling = recycling;
    }

    public void setTimeAdvance(int timedvance) {
        this.timeAdvance = timedvance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDone() {
        return done;
    }

    public int getPriority() {
        return priority;
    }

    public int getRecycling() {
        return recycling;
    }

    public int getTimeAdvance() {
        return timeAdvance;
    }

    public String getContent() {
        return content;
    }

    public String getTital() {
        return tital;
    }

    public String getType() {
        return type;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

}