package com.example.trail.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ServiceTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class BaiduMapServiceTest{
    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();
    @Test
    public void testWithStartedService() throws TimeoutException {
        mServiceRule.startService(
                new Intent(InstrumentationRegistry.getTargetContext(), BaiduMapService.class));
        //do something
        LocalBroadcastManager.getInstance(InstrumentationRegistry.getTargetContext()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        },new IntentFilter("SomeData"));
    }

    @Test
    public void testWithBoundService() throws TimeoutException {
        IBinder binder = mServiceRule.bindService(
                new Intent(InstrumentationRegistry.getTargetContext(), BaiduMapService.class));
        BaiduMapService service = ((BaiduMapService.LocalBinder) binder).getService();
        service.onCreate();
    }
}