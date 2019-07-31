package com.example.trail.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

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
            initList();
            displayLists(context,lists,times,views);
            checkTask(context,views);
            appWidgetManager.updateAppWidget(appWidgetId, views);

//            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if ("click0".equals(intent.getAction())) {
            Toast.makeText(context, "cklick0", Toast.LENGTH_SHORT).show();
        }
        if ("click1".equals(intent.getAction())) {
            Toast.makeText(context, "cklick1", Toast.LENGTH_SHORT).show();
        }
        if ("click2".equals(intent.getAction())) {
            Toast.makeText(context, "cklick2", Toast.LENGTH_SHORT).show();
        }
        if ("click3".equals(intent.getAction())) {
            Toast.makeText(context, "cklick3", Toast.LENGTH_SHORT).show();
        }
        if ("click4".equals(intent.getAction())) {
            Toast.makeText(context, "cklick4", Toast.LENGTH_SHORT).show();
        }
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
//        switch (size){
//            case 5:
//                views.setTextViewText(R.id.card_title4, tasks.get(4));
//                views.setTextViewText(R.id.card_time4,times.get(4));
//            case 4:
//                views.setTextViewText(R.id.card_title3, tasks.get(3));
//                views.setTextViewText(R.id.card_time3,times.get(3));
//            case 3:
//                views.setTextViewText(R.id.card_title2, tasks.get(2));
//                views.setTextViewText(R.id.card_time2,times.get(2));
//            case 2:
//                views.setTextViewText(R.id.card_title1, tasks.get(1));
//                views.setTextViewText(R.id.card_time1,times.get(1));
//            case 1:
//                views.setTextViewText(R.id.card_title0, tasks.get(0));
//                views.setTextViewText(R.id.card_time0,times.get(0));
//            case 0:
//                break;
//        }
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
        Intent checkIntent0=new Intent("click0");
        PendingIntent pendingIntent0 = PendingIntent.getBroadcast(context, R.id.btn_check0, checkIntent0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_check0,pendingIntent0);

        Intent checkIntent1=new Intent("click1");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, R.id.btn_check1, checkIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_check1,pendingIntent1);

        Intent checkIntent2=new Intent("click2");
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.btn_check2, checkIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_check2,pendingIntent2);

        Intent checkIntent3=new Intent("click3");
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, R.id.btn_check3, checkIntent3, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_check3,pendingIntent3);

        Intent checkIntent4=new Intent("click4");
        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, R.id.btn_check4, checkIntent4, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_check4,pendingIntent4);

    }
    private void freshTask(int i){
        lists.remove(i);
        times.remove(i);
    }
}

