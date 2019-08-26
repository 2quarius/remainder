package com.example.trail.Utility.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Utility.AlarmBroadcast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmUtils {
    private static AlarmManager alarmManager;
    private static Map<Integer, PendingIntent> alarms = new HashMap<>();
    public static void installAlarms(Context context,List<TaskCollector> taskCollectors){
        alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        installAll(context,taskCollectors);
    }
    private static void installAll(Context context, List<TaskCollector> taskCollectors){
        //install current alarm
        for (TaskCollector taskCollector:taskCollectors){
            for (Task task:taskCollector.getTasks()){
                if (!task.isDone()&&task.getRemindTime()!=null&&task.getRemindCycle()!=null){
                    switch (task.getRemindCycle()){
                        case SINGLE:
                            addNoneRepeatAlarm(context,task.getTitle(),TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis(),task.hashCode());
                            break;
                        case DAILY:
                            addRepeatAlarm(context,task.getTitle(),TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis(),AlarmManager.INTERVAL_DAY,task.hashCode());
                            break;
                        case WEEKLY:
                            addRepeatAlarm(context,task.getTitle(),TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis(),7*24*60*60*1000,task.hashCode());
                            break;
                        case MONTHLY: {
                            int dayInterval = TimeUtil.Date2Cal(task.getRemindTime()).getActualMaximum(Calendar.DAY_OF_MONTH);
                            addRepeatAlarm(context, task.getTitle(), TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis(), dayInterval * 24 * 60 * 60 * 1000,task.hashCode());
                            break;
                        }
                        case YEARLY: {
                            int year = TimeUtil.Date2Cal(task.getRemindTime()).get(Calendar.YEAR);
                            int dayInterval = ((year%4==0&&year%100!=0)||year%400==0)?366:365;
                            addRepeatAlarm(context, task.getTitle(), TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis(), dayInterval * 24 * 60 * 60 * 1000,task.hashCode());
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
    }

    private static void addRepeatAlarm(Context context,String title,long time,long interval,Integer id){
        Intent intent = new Intent(context, AlarmBroadcast.class);
        intent.putExtra("title",title);
        intent.setAction("startAlarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarms.get(id)==null) {
            alarms.put(id, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,interval,pendingIntent);
        }else {
            alarmManager.cancel(alarms.get(id));
            alarms.remove(id);
            alarms.put(id, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,interval,pendingIntent);
        }
    }
    private static void addNoneRepeatAlarm(Context context, String title, long time,Integer id) {
        // 设置闹钟触发动作
        Intent intent = new Intent(context, AlarmBroadcast.class);
        intent.putExtra("title",title);
        intent.setAction("startAlarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarms.get(id)==null) {
            alarms.put(id, pendingIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP,time, pendingIntent);
        }else {
            alarmManager.cancel(alarms.get(id));
            alarms.remove(id);
            alarms.put(id, pendingIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP,time, pendingIntent);
        }
    }
}
