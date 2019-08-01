package com.example.trail;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.baidu.mapapi.SDKInitializer;
import com.example.trail.Calendar.CalendarFragment;
import com.example.trail.Lists.ListsFragment;
import com.example.trail.Lists.SideMenuActivity;
import com.example.trail.Map.BaiduMapFragment;
import com.example.trail.NewTask.AddTaskActivity;
import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.RemindCycle;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Services.BaiduMapService;
import com.example.trail.Setting.SettingFragmnet;
import com.example.trail.Utility.AlarmBroadcast;
import com.example.trail.Utility.AlarmRemindActivity;
import com.example.trail.Utility.DataStorageHelper.StoreRetrieveData;
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

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , ListsFragment.callbackClass {
    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final int SWITCH_COLLECTION_REQUEST_CODE = 2;
    private boolean misScrolled;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private FloatingActionButton fab;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化百度地图，必须在 setContentView(...) 前调用！
        SDKInitializer.initialize(getApplicationContext());
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
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
        createTabIcons();
        //跳转button的动作
        fab=findViewById(R.id.fab_addTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent,ADD_TASK_REQUEST_CODE);
            }
        });
        tasks = new ArrayList<>();
        storeRetrieveData = new StoreRetrieveData(getApplicationContext(), FILENAME);
        setAlarm();
        Intent intent = new Intent(this, BaiduMapService.class);
        startService(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode==RESULT_OK&&data!=null){
            if (requestCode==ADD_TASK_REQUEST_CODE){
                tasks.add((Task) data.getSerializableExtra("task"));
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
//                else if (idx<taskCollectors.size()){
//                    index = idx;
//                }
//                else {
//                    String name = data.getStringExtra("name");
//                    int previousSize = taskCollectors.size();
//                    for (int i = previousSize;i<idx;i++)
//                    {
//                        taskCollectors.add(new TaskCollector());
//                    }
//                    taskCollectors.add(new TaskCollector(name,new ArrayList<Task>()));
//                }
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
        }
        setAlarm();
        super.onStart();
        setTheTheme();
    }
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
        storeRetrieveData = new StoreRetrieveData(getApplicationContext(), FILENAME);
        taskCollectors=getLocallyStoredData(storeRetrieveData);
        if (taskCollectors.size()>0){
            tasks = taskCollectors.get(0).getTasks();
        }
        for(int i=0;i<tasks.size();i++){
            boolean done = tasks.get(i).getDone();
            if (done==false) {
                Date tempDate = tasks.get(i).getRemindTime();
                if (tempDate!=null) {
                    RemindCycle cycle = tasks.get(i).getRemindCycle();
                    Calendar cal = Calendar.getInstance();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(tempDate);
                    if (calendar.getTimeInMillis()-cal.getTimeInMillis()>0) {
                        //Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, AlarmBroadcast.class);
                        intent.setAction("startAlarm");
                        intent.putExtra("title",tasks.get(i).getTitle());
                        intent.putExtra("description",tasks.get(i).getDescription());
                        //Toast.makeText(MainActivity.this,tasks.get(i).getTitle(),Toast.LENGTH_SHORT).show();
                        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        if (cycle==RemindCycle.DAILY) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24*60*60*1000, pendingIntent);
                        }
                        else if (cycle==RemindCycle.WEEKLY) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),7*24*60*60*1000, pendingIntent);
                        }
                        else if (cycle==RemindCycle.MONTHLY) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),30*24*60*60*1000, pendingIntent);
                        }
                        else if (cycle==RemindCycle.YEARLY) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),365*24*60*60*1000, pendingIntent);
                        }
                        else {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        }
                    }
                }
            }
        }
    }
    private void createTabIcons() {
        tabs.getTabAt(TabConstants.LISTS.getIndex()).setIcon(R.drawable.checklist);
        tabs.getTabAt(TabConstants.TIME.getIndex()).setIcon(R.drawable.calendar);
        tabs.getTabAt(TabConstants.SPACE.getIndex()).setIcon(R.drawable.map);
        tabs.getTabAt(TabConstants.SETTING.getIndex()).setIcon(R.drawable.settings);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListsFragment(), TabConstants.LISTS.getTitle());
        adapter.addFragment(new CalendarFragment(),TabConstants.TIME.getTitle());
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
}
