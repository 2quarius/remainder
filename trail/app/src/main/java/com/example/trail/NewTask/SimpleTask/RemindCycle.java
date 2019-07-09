package com.example.trail.NewTask.SimpleTask;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  RemindCycle {
    DAILY{"每天"},WEEKLY{"每周"},MONTHLY{"每月"},YEARLY{"每年"};
    private String cycle;
}
