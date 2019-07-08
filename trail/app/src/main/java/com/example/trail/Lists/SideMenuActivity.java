package com.example.trail.Lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trail.MainActivity;
import com.example.trail.R;
import com.google.android.material.navigation.NavigationView;

public class SideMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private MenuItem mPreMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (mPreMenuItem != null){
            return false;
        }
        switch (item.getItemId()){
            case R.id.nav_item_today:
            {
                System.out.println("show today's lists");
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
            case R.id.nav_item_collection:
            {
                System.out.println("show collection's lists");
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
            case R.id.nav_item_add:
            {
                System.out.println("add list file");
                mDrawerLayout.closeDrawers();
                break;
            }
            default:
                break;
        }
        item.setChecked(true);
        mPreMenuItem = item;
        return false;
    }
}
