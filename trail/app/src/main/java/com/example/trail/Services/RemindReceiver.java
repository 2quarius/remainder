package com.example.trail.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trail.Lists.SideMenuActivity;

public class RemindReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context,"收到消息",Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        intent = new Intent(context, SideMenuActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
