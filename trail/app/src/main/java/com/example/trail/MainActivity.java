package com.example.trail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;


import com.example.trail.Calendar.CalendarFragment;
import com.example.trail.Lists.ListsFragment;
import com.example.trail.Map.MapFragment;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
}
