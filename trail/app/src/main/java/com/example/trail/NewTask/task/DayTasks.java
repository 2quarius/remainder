package com.example.trail.NewTask.task;

import com.example.trail.NewTask.Task;

import java.util.Vector;

import lombok.Data;

@Data
public class DayTasks {
    public Vector<Task> tasks;
    public boolean addTask(Task task){return tasks.add(task);}
    public Task getFirstTask(){
        return tasks.firstElement();
    }
    public Task getLastTask(){
        return tasks.lastElement();
    }
}

