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

import java.util.ArrayList;

public class SideMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private MenuItem mPreMenuItem;
    private int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        Intent intent = getIntent();
        size = intent.getIntExtra("collector size",-1)+1;
        if (size>4){
            size = 4;
            ArrayList<String> titles = intent.getStringArrayListExtra("titles");
            for (int i = 0; i<titles.size();i++)
            {
                addCollector(titles.get(i));
            }
        }
        mNavigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_item_today:
            {
                System.out.println("show today's lists");
                item.setChecked(true);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index",0);
                setResult(RESULT_OK,intent);
                this.finish();
                break;
            }
            case R.id.nav_item_collection:
            {
                System.out.println("show collection's lists");
                item.setChecked(true);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index",1);
                setResult(RESULT_OK,intent);
                this.finish();
                break;
            }
            case R.id.nav_item_courses:
            {
                item.setChecked(true);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index",2);
                setResult(RESULT_OK,intent);
                this.finish();
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
            {
                if (id<size){
                    item.setChecked(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("index",id-1);
                    intent.putExtra("name",item.getTitle());
                    setResult(RESULT_OK,intent);
                    this.finish();
                }
                break;
            }
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
                if (text.getText().length()>0) {
                    //布局里添加一行
                    addCollector(text.getText().toString());
                    mPreMenuItem.setChecked(false);
                }

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
    private void addCollector(String name)
    {
        int id = size;
        size++;
        mNavigationView.getMenu().add(R.id.group_collections,id,id,name);
        mNavigationView.getMenu().getItem(id).setIcon(R.drawable.nav_default);
        mNavigationView.setNavigationItemSelectedListener(this);
    }
}
