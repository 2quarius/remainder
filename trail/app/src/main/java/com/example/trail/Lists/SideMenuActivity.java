package com.example.trail.Lists;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
        switch (item.getItemId()){
            case R.id.nav_item_today:
            {
                System.out.println("show today's lists");
                item.setChecked(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
            case R.id.nav_item_collection:
            {
                System.out.println("show collection's lists");
                item.setChecked(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
            case R.id.nav_item_add:
            {
                System.out.println("add list file");
                item.setChecked(true);
                showDialog();
                break;
            }
            default:
                break;
        }
        mPreMenuItem = item;
        return false;
    }
    private void showDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = LayoutInflater.from(this).inflate(R.layout.add_list_dialog,null);
        dialog.setTitle("添加清单");
        dialog.setView(view);
        dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText text = view.findViewById(R.id.editText);
                System.out.println(text.getText());
                mPreMenuItem.setChecked(false);

            }
        });
        dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                mPreMenuItem.setChecked(false);
            }
        });
        dialog.show();
    }
}
