package com.example.trail.NewTask.task;

public class task {
    private String starttime;
    private String endtime;
    private int recycling;
    private int timeadvance;
    private int priority;
    private String typename;
    private String tital;
    private String content;
    private int done;

    public void settime(String start,String end) {
        this.starttime = start;
        this.endtime = end;
    }

    public void setrecycling(int recy) {
        this.recycling = recy;
    }

    public void setadvan(int advance) {
        this.timeadvance = advance;
    }

    public void setprio(int level) {
        this.priority = level;
    }

    public void settype(String type) {
        this.typename = type;
    }

    public void settital(String tital) {
        this.tital = tital;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getstarttime() {
        return this.starttime;
    }

    public String getendtime() {
        return this.endtime;
    }

    public String gettype() {
        return this.typename;
    }

    public String getcontent() {
        return this.content;
    }

    public String gettital() {
        return this.tital;
    }

    public int getrecycling() {
        return this.recycling;
    }

    public int gettimeadvance() {
        return this.timeadvance;
    }

    public int getprio() {
        return this.priority;
    }
}
