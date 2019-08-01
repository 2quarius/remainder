package com.example.trail.Utility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("startAlarm")) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            Toast.makeText(context,title,Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(context,AlarmRemindActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            intent1.putExtra("title",title);
            intent1.putExtra("description",description);
            context.startActivity(intent1);
        }
        // 处理闹钟事件
        // 振动、响铃、或者跳转页面等
    }
}