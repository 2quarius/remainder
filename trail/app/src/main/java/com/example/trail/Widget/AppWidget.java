package com.example.trail.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.AddTaskActivity;
import com.example.trail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    private List<String> lists=new ArrayList<>();
    private List<String> times=new ArrayList<>();
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setOnClickPendingIntent(R.id.add_widget, PendingIntent.getActivity(context, 0x001, new Intent(context, AddTaskActivity.class), 0));
        views.setOnClickPendingIntent(R.id.set_widget,PendingIntent.getActivity(context,0,new Intent(context, MainActivity.class),0));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        for (int appWidgetId : appWidgetIds) {
//            views.setTextViewText(R.id.widget_title,"nice?");
//            views.setTextViewText(R.id.card_title4,"吃饭");
            initList();
            displayLists(context,lists,times,views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
//            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void initList(){
        lists.add("吃饭");
        lists.add("睡觉");
        lists.add("打豆豆");
        lists.add("睡觉");
        lists.add("吃饭");
        times.add("今天");
        times.add("明天");
        times.add("今天");
        times.add("今天");
        times.add("今天");
    }


    private void displayLists(Context context,List<String> tasks,List<String> times,RemoteViews views){
        int size=tasks.size();
        if(size==0) return;
        else if(size==1){
            views.setTextViewText(R.id.card_title0, tasks.get(0));
            views.setTextViewText(R.id.card_time0,times.get(0));
            return;
        }
        else if(size==2){
            views.setTextViewText(R.id.card_title0, tasks.get(0));
            views.setTextViewText(R.id.card_time0,times.get(0));

            views.setTextViewText(R.id.card_title1, tasks.get(1));
            views.setTextViewText(R.id.card_time1,times.get(1));
            return;
        }
        else if(size==3){
            views.setTextViewText(R.id.card_title0, tasks.get(0));
            views.setTextViewText(R.id.card_time0,times.get(0));

            views.setTextViewText(R.id.card_title1, tasks.get(1));
            views.setTextViewText(R.id.card_time1,times.get(1));

            views.setTextViewText(R.id.card_title2, tasks.get(2));
            views.setTextViewText(R.id.card_time2,times.get(2));
            return;
        }
        else if(size==4){
            views.setTextViewText(R.id.card_title0, tasks.get(0));
            views.setTextViewText(R.id.card_time0,times.get(0));

            views.setTextViewText(R.id.card_title1, tasks.get(1));
            views.setTextViewText(R.id.card_time1,times.get(1));

            views.setTextViewText(R.id.card_title2, tasks.get(2));
            views.setTextViewText(R.id.card_time2,times.get(2));

            views.setTextViewText(R.id.card_title3, tasks.get(3));
            views.setTextViewText(R.id.card_time3,times.get(3));
            return;
        }
        else if(size==5){
            views.setTextViewText(R.id.card_title0, tasks.get(0));
            views.setTextViewText(R.id.card_time0,times.get(0));

            views.setTextViewText(R.id.card_title1, tasks.get(1));
            views.setTextViewText(R.id.card_time1,times.get(1));

            views.setTextViewText(R.id.card_title2, tasks.get(2));
            views.setTextViewText(R.id.card_time2,times.get(2));

            views.setTextViewText(R.id.card_title3, tasks.get(3));
            views.setTextViewText(R.id.card_time3,times.get(3));

            views.setTextViewText(R.id.card_title4, tasks.get(4));
            views.setTextViewText(R.id.card_time4,times.get(4));
            return;
        }
    }

    private void checkTask(Context context,RemoteViews views){

    }
}

