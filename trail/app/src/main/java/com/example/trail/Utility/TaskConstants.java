package com.example.trail.Utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskConstants {
    TITLE("title"),
    DESCRIPTION("description"),
    TAGS("tags"),
    START_TIME("start_time"),
    EXPIRE_TIME("expire_time"),
    REMIND_TIME("remind_time"),
    REMIND_CYCLE("remind_cycle"),
    PRIORITY("priority"),
    LOCATION("location"),
    DONE("done");
    private String s;
}
