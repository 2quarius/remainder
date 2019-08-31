package com.example.trail.NewTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ClipService extends Service {
    public  String TAG = "ClipService";

    @Override
    public void onCreate() {
        super.onCreate();
        //定义log方法在后台打印
        Log.e(TAG, "onCreate() ++++++++++++++++++++++++++++++++++++++++++"+Thread.currentThread().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() ++++++++++++++++++++++++++++++++++++++++++++");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand() ++++++++++++++++++++++++++++++++++++++++");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
