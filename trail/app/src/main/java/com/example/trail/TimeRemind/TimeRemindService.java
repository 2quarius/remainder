package com.example.trail.TimeRemind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.RemindCycle;
import com.example.trail.NewTask.SimpleTask.Task;

import java.util.Calendar;
import java.util.Date;

public class TimeRemindService extends Service {
    int mStartMode;
    Task task;
    AlarmManager alarm;
    Intent intent;
    PendingIntent remind;

    private void addTasktoAlarm() {
        Date timesetted = task.remindTime;
        Date timenow = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timesetted);
        long timerange = timesetted.getTime() - timenow.getTime();
        if (timerange>=0) {
            if (task.remindCycle == RemindCycle.SINGLE) {
                long diff=cal.getTimeInMillis()- SystemClock.elapsedRealtime();
                alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-diff, remind);
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
                long diff=cal.getTimeInMillis()- SystemClock.elapsedRealtime();
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-diff, cyclingTime, remind);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent data, int flags, int startId) {
        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, RemindActivity.class);
        task =  (Task) data.getSerializableExtra("Task");
        if (task==null) {
            //Toast toast=Toast.makeText(TimeRemindService.this,"Toast提示消息",Toast.LENGTH_SHORT    );
            //toast.show();
            return mStartMode;
        }
        intent.putExtra("task",task);
        remind = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (task.remindme==true) {
            addTasktoAlarm();
        }
        else {
            alarm.cancel(remind);
        }
        return mStartMode;
    }
}
