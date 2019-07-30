package com.example.trail.Utility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("startAlarm")) {
            //Task task = (Task) intent.getSerializableExtra("task");
            //Toast.makeText(context,task.title,Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(context,AlarmRemindActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            //intent1.putExtra("task",task);
            context.startActivity(intent1);
        }
        // 处理闹钟事件
        // 振动、响铃、或者跳转页面等
    }
}