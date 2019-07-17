package com.example.trail.TimeRemind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.trail.NewTask.SimpleTask.RemindCycle;
import com.example.trail.NewTask.SimpleTask.Task;

import java.util.Date;

public class TimeRemindService extends Service {
    int mStartMode;
    Task task;
    AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
    Intent intent = new Intent(this, RemindActivity.class);
    PendingIntent remind;

    private void addTasktoAlarm() {
        Date timesetted = task.remindTime;
        Date timenow = new Date(System.currentTimeMillis());
        long timerange = timesetted.getTime() - timenow.getTime();
        if (task.remindCycle == RemindCycle.SINGLE) {
            if (timerange>=0) {
                alarm.set(AlarmManager.RTC_WAKEUP, timesetted.getTime(), remind);
            }
        }
        else {
            long cyclingTime = 1000000000;
            switch (task.remindCycle) {
                case DAILY:
                    cyclingTime = 1000 * 60 * 60 * 24;
                    break;
                case WEEKLY:
                    cyclingTime = 1000 * 60 * 60 * 24 * 7;
                    break;
            }
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, timesetted.getTime(), cyclingTime, remind);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        task =  (Task) intent.getSerializableExtra("Task");
        intent.putExtra("task",task);
        remind = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        addTasktoAlarm();
        return mStartMode;
    }
}
