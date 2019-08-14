package com.example.trail;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.baidu.mapapi.SDKInitializer;
import com.example.trail.Calendar.CalendarFragment;
import com.example.trail.Lists.ListsFragment;
import com.example.trail.Lists.SideMenuActivity;
import com.example.trail.Map.BaiduMapFragment;
import com.example.trail.NewTask.AddTaskActivity;
import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Services.BaiduMapService;
import com.example.trail.Setting.SettingFragmnet;
import com.example.trail.Utility.AlarmBroadcast;
import com.example.trail.Utility.DataStorageHelper.StoreRetrieveData;
import com.example.trail.Utility.EnumPack.KeyConstants;
import com.example.trail.Utility.EnumPack.TabConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , ListsFragment.callbackClass ,SettingFragmnet.backClass {
    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final int SWITCH_COLLECTION_REQUEST_CODE = 2;
    private boolean misScrolled;
    /**
     * layout elements
     */
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private FloatingActionButton mFab;
    private FloatingNavigationView mFloatingNavView;

    private CalendarFragment calendarFragment;
    /**
     * task collector indicators
     */
    private int index=0;
    private List<TaskCollector> taskCollectors;
    private List<Task> tasks;
    private StoreRetrieveData storeRetrieveData;


    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public static final String FILENAME = "tasks.json";
    final  private String FILE_NAME2 = "information.txt";
    final  private String FILE_NAME3 = "theme.txt";
    public String account;
    public String USER_NAME;
    Fragment settingfragment;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    private void setTheTheme() {
        String theme = "";
        try {
            FileInputStream ios = openFileInput(FILE_NAME3);
            byte[] temp = new byte[10];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            theme = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
        if (theme.equals("purple")) {
            setTheme(R.style.LightTheme);
        }
        else if (theme.equals("black")){
            setTheme(R.style.NightTheme);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化百度地图，必须在 setContentView(...) 前调用！
        SDKInitializer.initialize(getApplicationContext());
        Bmob.initialize(this, KeyConstants.BMOB_SIXPLUS.getKey());
        try {
            FileInputStream ios = openFileInput(FILE_NAME2);
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            account = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
            account = "failed";
        }
        setTheTheme();
        setContentView(R.layout.activity_main);
        // Setting ViewPager for each Tabs
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        // Set Tabs inside Toolbar
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mViewPager);
        createTabIcons();
        //跳转button的动作
        mFab =findViewById(R.id.fab_addTask);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent,ADD_TASK_REQUEST_CODE);
            }
        });
        mFloatingNavView = findViewById(R.id.floating_view);
        tasks = new ArrayList<>();
        storeRetrieveData = new StoreRetrieveData(getApplicationContext(), FILENAME);
        setAlarm();
        USER_NAME=readUsername();
        Intent intent = new Intent(this, BaiduMapService.class);
        startService(intent);
    }

    /**
     * solve result when startActivityForResult ends
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode==RESULT_OK&&data!=null){
            if (requestCode==ADD_TASK_REQUEST_CODE){
                tasks.add((Task) data.getSerializableExtra("task"));
                calendarFragment.refresh(tasks);
            }
            else if (requestCode==SWITCH_COLLECTION_REQUEST_CODE){
                int idx = data.getIntExtra("index",-1);
                if (idx<0){
                    return;
                }
                else {
                    if (data.getIntegerArrayListExtra("indexs") != null) {
                        ArrayList<Integer> indexs = data.getIntegerArrayListExtra("indexs");
                        for (int i = indexs.size()-1;i>=0;i--)
                        {
                            taskCollectors.remove(indexs.get(i)+3);
                        }
                    }
                    if (data.getStringArrayListExtra("added")!=null){
                        ArrayList<String> added = data.getStringArrayListExtra("added");
                        for (String title:added)
                        {
                            taskCollectors.add(new TaskCollector(title,new ArrayList<Task>()));
                        }
                    }
                    if (idx<taskCollectors.size()){
                        index = idx;
                    }
                }
            }
        }
        //调用子fragment的 onActivityResult
        super.onActivityResult(requestCode,resultCode,data);
        try {
            storeRetrieveData.saveToFile((ArrayList<TaskCollector>) taskCollectors);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * after onCreateView, get latest taskCollectors and tasks
     */
    @Override
    protected void onStart(){
        taskCollectors = getLocallyStoredData(storeRetrieveData);
        int size = taskCollectors.size();
        if (size<3){
            switch (size){
                case 0:
                    taskCollectors.add(new TaskCollector("today",new ArrayList<Task>()));
                case 1:
                    taskCollectors.add(new TaskCollector("collection",new ArrayList<Task>()));
                case 2:
                    taskCollectors.add(new TaskCollector("courses",new ArrayList<Task>()));
                default:
                    break;
            }
        }
        if (taskCollectors.size()>0&&index<taskCollectors.size()){
            tasks = taskCollectors.get(index).getTasks();
            switch (index){
                case 0:
                    taskCollectors.get(0).setName("today");
                case 1:
                    taskCollectors.get(1).setName("collection");
                case 2:
                    taskCollectors.get(2).setName("courses");
                default:
                    break;
            }
            mFloatingNavView.setImageBitmap(textAsBitmap(taskCollectors.get(index).getName(), 40, getColor(R.color.colorPrimary)));
        }
        super.onStart();
        setTheTheme();
    }

    /**
     * do store when start another activity
     */
    @Override
    public void onPause() {
        super.onPause();
        try {
            storeRetrieveData.saveToFile((ArrayList<TaskCollector>) taskCollectors);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setTasks(List<Task> mTasks) {
        tasks = mTasks;
        calendarFragment.refresh(mTasks);
    }
    @Override
    public void sTasks(List<Task> bTasks) {
        tasks.addAll(bTasks);
    }
    public List<Task> getTasks(){
        return tasks;
    }
    private static ArrayList<TaskCollector> getLocallyStoredData(StoreRetrieveData storeRetrieveData) {
        ArrayList<TaskCollector> collectors = null;
        try {
            collectors = storeRetrieveData.loadFromFile();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if (collectors == null) {
            collectors = new ArrayList<>();
        }
        return collectors;

    }

    //设置闹钟
    private void setAlarm(){
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmBroadcast.class);
        intent.setAction("startAlarm");
        pendingIntent = PendingIntent.getBroadcast(this, 110, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        storeRetrieveData = new StoreRetrieveData(getApplicationContext(), FILENAME);
        taskCollectors=getLocallyStoredData(storeRetrieveData);
        if (taskCollectors.size()>0){
            tasks = taskCollectors.get(0).getTasks();
        }
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getExpireTime()!=null)
            {
                Date tempDate=tasks.get(i).getExpireTime();
                Calendar cal=Calendar.getInstance();
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(tempDate);
                long timeDiff=cal.getTimeInMillis()-SystemClock.elapsedRealtime();
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-timeDiff+60*1000, pendingIntent);
            }
        }
    }
    private void createTabIcons() {
        mTabs.getTabAt(TabConstants.LISTS.getIndex()).setIcon(R.drawable.checklist);
        mTabs.getTabAt(TabConstants.TIME.getIndex()).setIcon(R.drawable.calendar);
        mTabs.getTabAt(TabConstants.SPACE.getIndex()).setIcon(R.drawable.map);
        mTabs.getTabAt(TabConstants.SETTING.getIndex()).setIcon(R.drawable.settings);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListsFragment(), TabConstants.LISTS.getTitle());
        calendarFragment = new CalendarFragment();
        adapter.addFragment(calendarFragment,TabConstants.TIME.getTitle());
        adapter.addFragment(new BaiduMapFragment(), TabConstants.SPACE.getTitle());
        settingfragment = new SettingFragmnet();
        adapter.addFragment(settingfragment,TabConstants.SETTING.getTitle());
        Bundle bundle = new Bundle();
        bundle.putString("account",account);
        settingfragment.setArguments(bundle);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                misScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                misScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (mViewPager.getCurrentItem() == 0 && !misScrolled) {
                    Intent intent = new Intent(this,SideMenuActivity.class);
                    int size = taskCollectors.size();
                    intent.putExtra("collector size",size);
                    if (size>3){
                        ArrayList<String> titles = new ArrayList<>();
                        for (int i = 3;i<size;i++)
                        {
                            titles.add(taskCollectors.get(i).getName());
                        }
                        intent.putStringArrayListExtra("titles",titles);
                    }
                    startActivityForResult(intent,SWITCH_COLLECTION_REQUEST_CODE);
                }
                misScrolled = true;
                break;
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private String  readUsername(){
        String textContent = "";
        try {
            FileInputStream ios = openFileInput("information.txt");
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            textContent = sb.toString();
        }catch (Exception e) {
        }
        return textContent;
    }
    //method to convert your text to image
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) ((baseline + paint.descent() + 0.0f)*1.6);
        Bitmap image = Bitmap.createBitmap(width*2, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 40, baseline, paint);
        return image;
    }
}

