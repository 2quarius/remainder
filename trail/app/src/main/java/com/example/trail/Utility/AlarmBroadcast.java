package com.example.trail.Utility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("startAlarm".equals(intent.getAction())) {
            Log.d("Alarm","test");
            // 处理闹钟事件
            Toast.makeText(context, "提醒："+intent.getCharSequenceExtra("title")+"任务未完成" , Toast.LENGTH_LONG).show();
        }
    }
}