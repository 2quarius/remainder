package com.example.trail.TimeRemind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.trail.NewTask.SimpleTask.Task;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Task task = (Task) intent.getSerializableExtra("Task");
        if ("startAlarm".equals(intent.getAction())) {
            Log.d("Alarm","test");
            Toast.makeText(context, "闹钟提醒", Toast.LENGTH_LONG).show();
            // 处理闹钟事件
            // 振动、响铃、或者跳转页面等
        }
    }
}

