package com.example.trail;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

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
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Services.AlarmManageService;
import com.example.trail.Services.BaiduMapService;
import com.example.trail.Setting.SettingFragmnet;
import com.example.trail.Utility.DataStorageHelper.StoreRetrieveData;
import com.example.trail.Utility.EnumPack.TabConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , ListsFragment.callbackClass {
    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final int RETURNED_MODIFY_TASK_REQUEST_CODE = 65545;
    private boolean misScrolled;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private FloatingActionButton fab;
    private List<Task> tasks;
    private StoreRetrieveData storeRetrieveData;
    public static final String FILENAME = "tasks.json";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化百度地图，必须在 setContentView(...) 前调用！
        SDKInitializer.initialize(getApplicationContext());
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
            try {
                storeRetrieveData.saveToFile((ArrayList<Task>) tasks);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        //调用子fragment的 onActivityResult
        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    protected void onStart(){
        super.onStart();
        tasks = getLocallyStoredData(storeRetrieveData);

        Bundle bundle = new Bundle();
        bundle.putSerializable("task",tasks.get(0));
        AlarmManageService.addAlarm(getApplicationContext(),0,bundle,1);
    }
    @Override
    public void onPause() {
        super.onPause();
        try {
            storeRetrieveData.saveToFile((ArrayList<Task>) tasks);
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
    private static ArrayList<Task> getLocallyStoredData(StoreRetrieveData storeRetrieveData) {
        ArrayList<Task> items = null;
        try {
            items = storeRetrieveData.loadFromFile();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if (items == null) {
            items = new ArrayList<>();
        }
        return items;

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
        adapter.addFragment(new SettingFragmnet(),TabConstants.SETTING.getTitle());
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
                    startActivityForResult(new Intent(this, SideMenuActivity.class),2);
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
