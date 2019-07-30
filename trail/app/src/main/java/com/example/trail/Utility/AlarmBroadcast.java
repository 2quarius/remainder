package com.example.trail.Utility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "闹钟提醒", Toast.LENGTH_LONG).show();
        // 处理闹钟事件
        // 振动、响铃、或者跳转页面等
    }
}