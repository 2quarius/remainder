package com.example.trail.NewTask.SimpleTask;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {
    EUGEN (3),MIDDLE(2),LITTLE(1),NONE(0);
    private Integer priority;
}