package com.example.trail.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.RemoteViews;

import androidx.viewpager.widget.ViewPager;

import com.example.trail.NewTask.AddTaskActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;

import java.util.ArrayList;
import java.util.List;


public class AppWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        // Instruct the widget manager to update the widget
        List<String> tasks = new ArrayList<>();
        tasks.add("吃饭");
        tasks.add("睡觉");
        tasks.add("打豆豆");

        List<String> times = new ArrayList<>();
        times.add("今天");
        times.add("明天");
        times.add("现在");

        views.setTextViewText(R.id.card_title0, tasks.get(0));
        views.setTextViewText(R.id.card_time0,times.get(0));

        views.setTextViewText(R.id.card_title1, tasks.get(1));
        views.setTextViewText(R.id.card_time1,times.get(1));

        views.setTextViewText(R.id.card_title2, tasks.get(2));
        views.setTextViewText(R.id.card_time2,times.get(2));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        Intent skipIntent = new Intent(context, AddTaskActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.add_widget, pi);


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }





//    private void displayLists(Context context,List<String> tasks,List<String> times){
//        int size=tasks.size();
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//        if(size==0) return;
//        else if(size==1){
//            views.setTextViewText(R.id.card_title0, tasks.get(0));
//            views.setTextViewText(R.id.card_time0,times.get(0));
//            return;
//        }
//        else if(size==2){
//            views.setTextViewText(R.id.card_title0, tasks.get(0));
//            views.setTextViewText(R.id.card_time0,times.get(0));
//
//            views.setTextViewText(R.id.card_title1, tasks.get(1));
//            views.setTextViewText(R.id.card_time1,times.get(1));
//            return;
//        }
//        else if(size==3){
//            views.setTextViewText(R.id.card_title0, tasks.get(0));
//            views.setTextViewText(R.id.card_time0,times.get(0));
//
//            views.setTextViewText(R.id.card_title1, tasks.get(1));
//            views.setTextViewText(R.id.card_time1,times.get(1));
//
//            views.setTextViewText(R.id.card_title2, tasks.get(2));
//            views.setTextViewText(R.id.card_time2,times.get(2));
//            return;
//        }
//        else if(size==4){
//            views.setTextViewText(R.id.card_title0, tasks.get(0));
//            views.setTextViewText(R.id.card_time0,times.get(0));
//
//            views.setTextViewText(R.id.card_title1, tasks.get(1));
//            views.setTextViewText(R.id.card_time1,times.get(1));
//
//            views.setTextViewText(R.id.card_title2, tasks.get(2));
//            views.setTextViewText(R.id.card_time2,times.get(2));
//
//            views.setTextViewText(R.id.card_title3, tasks.get(3));
//            views.setTextViewText(R.id.card_time3,times.get(3));
//            return;
//        }
//        else if(size==4){
//            views.setTextViewText(R.id.card_title0, tasks.get(0));
//            views.setTextViewText(R.id.card_time0,times.get(0));
//
//            views.setTextViewText(R.id.card_title1, tasks.get(1));
//            views.setTextViewText(R.id.card_time1,times.get(1));
//
//            views.setTextViewText(R.id.card_title2, tasks.get(2));
//            views.setTextViewText(R.id.card_time2,times.get(2));
//
//            views.setTextViewText(R.id.card_title3, tasks.get(3));
//            views.setTextViewText(R.id.card_time3,times.get(3));
//
//            views.setTextViewText(R.id.card_title4, tasks.get(4));
//            views.setTextViewText(R.id.card_time4,times.get(4));
//            return;
//        }
//    }
}

