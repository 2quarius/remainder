package com.example.trail.NewTask.task;

import java.util.Vector;

import lombok.Data;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
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

