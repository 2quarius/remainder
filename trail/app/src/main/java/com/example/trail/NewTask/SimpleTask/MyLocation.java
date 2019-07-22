package com.example.trail.NewTask.SimpleTask;

import android.location.Location;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyLocation implements Serializable {
    private transient Location location;//would not be available when trans in network

    public MyLocation(String string) {
        location = new Location(string);
    }
}
