package com.example.trail.Utility;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.trail.NewTask.SimpleTask.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TaskManager {
    //设置4级的priority的队列，其中ddl越靠前就排在队头，ddl相同完成任务所需时间越短越靠前
    private PriorityQueue<Task> eugenQueue;
    private PriorityQueue<Task> middleQueue;
    private PriorityQueue<Task> littleQueue;
    private PriorityQueue<Task> noneQueue;
    private Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            int result = compare(t2.getExpireTime(),t1.getExpireTime());
            return result!=0?result:compare(t2.getStartTime(),t1.getStartTime());
        }
        private int compare(Date d1,Date d2){
            if (d1!=null&&d2!=null){
                if (d1.before(d2)){
                    return 1;
                }
                else if (d1.after(d2)){
                    return -1;
                }
                else {
                    return 0;
                }
            }
            else if (d1==null&&d2==null){
                return 0;
            }
            else {
                if (d1==null){
                    return -1;
                }
                else return 1;
            }
        }
    };

    public TaskManager() {
        eugenQueue = new PriorityQueue<>(comparator);
        middleQueue = new PriorityQueue<>(comparator);
        littleQueue = new PriorityQueue<>(comparator);
        noneQueue = new PriorityQueue<>(comparator);
    }

    public List<Task> getManagedTasks(List<Task> taskList)
    {
        //将taskList排入队列
        initQueues(taskList);
        //依次获取4级queue内ddl在24小时内的任务
        List<Task> result = new ArrayList<>();
        Date tomorrow = tomorrow(new Date());
        List<PriorityQueue<Task>> queueList = new ArrayList<>();
        queueList.add(eugenQueue);
        queueList.add(middleQueue);
        queueList.add(littleQueue);
        queueList.add(noneQueue);
        for (int i = 0;i < queueList.size();i++){
            for (Task task:queueList.get(i))
            {
                if (task.getExpireTime().before(tomorrow)){
                    result.add(task);
                }
                else {
                    break;
                }
            }
        }
        return result;
    }

    private void initQueues(List<Task> taskList) {
        for (Task task:taskList){
            switch (task.getPriority()){
                case NONE:
                    noneQueue.add(task);
                    break;
                case EUGEN:
                    eugenQueue.add(task);
                    break;
                case LITTLE:
                    littleQueue.add(task);
                    break;
                case MIDDLE:
                    middleQueue.add(task);
                    break;
                default:
                    break;
            }
        }
    }

    private Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE,1);
        return calendar.getTime();
    }
}
