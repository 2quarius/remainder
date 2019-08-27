package com.example.trail.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.trail.R;

import java.util.ArrayList;
import java.util.List;

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final static String TAG="Widget";
    private Context mContext;
    private int mAppWidgetId;
    private List<String> lists=new ArrayList<>();

    /**
     * 构造ListRemoteViewsFactory
     */
    public ListRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        //  HashMap<String, Object> map;

        // 获取 item_widget_device.xml 对应的RemoteViews
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.calendar_list_item);
        String title=lists.get(position);
        // 设置 第position位的“视图”的数据
        //  rv.setImageViewResource(R.id.iv_lock, ((Integer) map.get(IMAGE_ITEM)).intValue());
        rv.setTextViewText(R.id.item_title, title);

//        // 设置 第position位的“视图”对应的响应事件
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtra("Type", 0);
//        fillInIntent.putExtra(AppWidget.COLLECTION_VIEW_EXTRA, position);
//        rv.setOnClickFillInIntent(R.id.rl_widget_task, fillInIntent);


//        Intent lockIntent = new Intent();
//        lockIntent.putExtra(AppWidget.COLLECTION_VIEW_EXTRA, position);
//        lockIntent.putExtra("Type", 1);
//        rv.setOnClickFillInIntent(R.id.iv_lock, lockIntent);
//
//        Intent unlockIntent = new Intent();
//        unlockIntent.putExtra("Type", 2);
//        unlockIntent.putExtra(ListWidgetProvider.COLLECTION_VIEW_EXTRA, position);
//        rv.setOnClickFillInIntent(R.id.iv_unlock, unlockIntent);

        return rv;
    }


    /**
     * 初始化ListView的数据
     */
    private void initListViewData() {
        lists.add("吃饭");
        lists.add("睡觉");
        lists.add("打豆豆");
    }
    private static int i;
    public static void refresh(){
        i++;
    }

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        // 初始化“集合视图”中的数据
        initListViewData();
    }

    @Override
    public int getCount() {
        // 返回“集合视图”中的数据的总数
        return lists.size();
    }

    @Override
    public long getItemId(int position) {
        // 返回当前项在“集合视图”中的位置
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        // 只有一类 ListView
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
        lists.clear();
    }
}
