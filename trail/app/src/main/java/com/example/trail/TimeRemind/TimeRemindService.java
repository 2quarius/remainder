package com.example.trail.TimeRemind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
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
        long systemtime = SystemClock.elapsedRealtime();
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        cal.setTime(timenow);
        calendar.setTime(timesetted);
        long timerange = calendar.getTimeInMillis() - cal.getTimeInMillis();
        long timeinput = timerange + systemtime;

        Log.d("timenow", ""+cal.getTimeInMillis());
        Log.d("timeset", ""+calendar.getTimeInMillis());
        Log.d("timerange", ""+timerange);
        Log.d("timesystem", ""+systemtime);
        Log.d("timesyinput", ""+(timeinput));
        if (task.remindCycle == RemindCycle.SINGLE) {
            alarm.set(AlarmManager.RTC_WAKEUP, timeinput, remind);
            Toast toast=Toast.makeText(TimeRemindService.this,"Toast提示消息",Toast.LENGTH_SHORT    );
            toast.show();
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
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, timeinput, cyclingTime, remind);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent data, int flags, int startId) {
        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        task = (Task) data.getSerializableExtra("task");
        if (task==null) {
            //Toast toast=Toast.makeText(TimeRemindService.this,"Toast提示消息",Toast.LENGTH_SHORT    );
            //toast.show();
            return mStartMode;
        }
        intent = new Intent(this, RemindActivity.class);
        intent.putExtra("task",task);
        intent.setAction("startAlarm");
        remind = PendingIntent.getActivity(this, 110, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (task.remindme==true) {
            addTasktoAlarm();
        }
        else {
            alarm.cancel(remind);
        }
        return mStartMode;
    }
}
