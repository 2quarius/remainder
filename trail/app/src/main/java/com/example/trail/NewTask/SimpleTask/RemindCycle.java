package com.example.trail.NewTask.SimpleTask;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  RemindCycle {
    SINGLE("单次"),DAILY("每天"),WEEKLY("每周"),MONTHLY("每月"),YEARLY("每年");
    private String cycle;

    public static RemindCycle match(String name)
    {
        for (RemindCycle r:RemindCycle.values())
        {
            if (r.cycle.equals(name)){
                return r;
            }
        }
        return null;
    }
}
