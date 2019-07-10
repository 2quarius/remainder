package com.example.trail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;



import com.example.trail.Calendar.CalendarFragment;
import com.example.trail.NewTask.AddTaskActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Utility.StoreRetrieveData;
import com.example.trail.Utility.TabConstants;
import com.example.trail.Lists.SideMenuActivity;
import com.example.trail.Setting.SettingFragmnet;
import com.example.trail.Lists.ListsFragment;
import com.example.trail.Map.MapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private boolean misScrolled;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private FloatingActionButton fab;
    private List<Task> tasks;
    private StoreRetrieveData storeRetrieveData;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//                NewTaskActivity.monthTasks=monthTasks;
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        tasks = getLocallyStoredData(storeRetrieveData);
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
//        adapter.addFragment(new ListsFragment(), "lists");
        adapter.addFragment(new ListsFragment(), TabConstants.LISTS.getTitle());
        adapter.addFragment(new CalendarFragment(),TabConstants.TIME.getTitle());
        adapter.addFragment(new MapFragment(),TabConstants.SPACE.getTitle());
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
                    startActivity(new Intent(this, SideMenuActivity.class));
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
