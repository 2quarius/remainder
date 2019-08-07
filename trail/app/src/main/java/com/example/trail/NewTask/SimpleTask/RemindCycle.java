package com.example.trail.NewTask.SimpleTask;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  RemindCycle {
    SINGLE("单次"),DAILY("每天"),WEEKLY("每7天"),MONTHLY("每30天"),YEARLY("每365天");
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
