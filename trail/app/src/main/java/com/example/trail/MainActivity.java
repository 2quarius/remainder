package com.example.trail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.widget.TextView;


import com.example.trail.Calendar.CalendarFragment;
import com.example.trail.NewTask.NewTaskActivity;
import com.example.trail.EventsObject.Event;
import com.example.trail.EventsObject.MonthEvent;
import com.example.trail.NewTask.NewTaskActivity;
import com.example.trail.NewTask.task.MonthTasks;
import com.example.trail.Setting.SettingFragmnet;
import com.example.trail.Lists.ListsFragment;
import com.example.trail.Map.MapFragment;
import com.google.android.material.tabs.TabLayout;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAddTask;
    private MonthTasks monthTasks;
    private TabLayout tabs;
    private ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting ViewPager for each Tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //初始化monthTasks用来存储数据
        monthTasks=new MonthTasks();

        //Set FloatingActonButton action
        fabAddTask=findViewById(R.id.fab_addTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, NewTaskActivity.class);
                NewTaskActivity.monthTasks=monthTasks;
                startActivity(intent);
            }
        createTabIcons();
//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            VectorDrawableCompat indicator
//                    = VectorDrawableCompat.create(getResources(), R.drawable.checklist, getTheme());
//            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.colorAccent,getTheme()));
//            supportActionBar.setHomeAsUpIndicator(indicator);
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
//        }
    }
    private void createTabIcons() {
        tabs.getTabAt(0).setIcon(R.drawable.checklist);

        tabs.getTabAt(1).setIcon(R.drawable.calendar);
//        tabs.getTabAt(2).setIcon(R.drawable.map);
//        tabs.getTabAt(3).setIcon(R.drawable.settings);


    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListsFragment(), "lists");
        adapter.addFragment(new CalendarFragment(),"time");
        adapter.addFragment(new MapFragment(),"space");
        adapter.addFragment(new SettingFragmnet(),"settings");
        adapter.addFragment(new ListsFragment(), "LISTS");
        adapter.addFragment(new CalendarFragment(),"TIME");
//        adapter.addFragment(new MapFragment(),"SPACE");
        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        //private List<Drawable> icons;

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

    FileOutputStream outputStream;
    FileInputStream inputStream;



    public void saveinfile(String data) {
        try {
            outputStream.write(data.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String ReadFile(String month) {
        String FILENAME = month;
        String out = "";
        byte[] buffer = null;
        try {
            inputStream = openFileInput(FILENAME);
            try {
                // 获取文件内容长度
                int fileLen = inputStream.available();
                // 读取内容到buffer
                buffer = new byte[fileLen];
                inputStream.read(buffer);
                out = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return out;
    }

    public void Save() {
        String month = "201907";
        String data;
        String FILENAME = month;
        try {
            outputStream = openFileOutput(FILENAME, Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        data = "一只鸭子";
        saveinfile(data);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Load() {
        String filecontent = ReadFile("201907");
        return filecontent;
    }
}
