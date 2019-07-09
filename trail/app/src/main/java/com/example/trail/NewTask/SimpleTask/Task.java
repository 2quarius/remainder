package com.example.trail.NewTask.SimpleTask;

import android.location.Location;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String title;
    private String description;
    private List<String> tags;

    private Date startTime;
    private Date expireTime;

    private Date remindTime;
    private String remindCycle;

    private Integer priority;

    private Location location;

    private Boolean done;
    public Boolean isDone()
    {
        return done == null ? false : done;
    }

}
