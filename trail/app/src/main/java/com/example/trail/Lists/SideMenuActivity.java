package com.example.trail.Lists;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
    private int size;//4+operated.size+added.size
    //初始collector的title
    private ArrayList<String> origin = new ArrayList<>();
    //删除操作作用的title
    private ArrayList<String> operated = new ArrayList<>();
    //实际新增的title
    private ArrayList<String> added = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.P)
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
                origin.add(titles.get(i));
                operated.add(titles.get(i));
            }
            size = mNavigationView.getMenu().size();
        }
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setClickDeleteListener(final int i) {
        //i相对于size(i>=4)
        MenuItem item = mNavigationView.getMenu().findItem(i);
        Button button = new Button(getApplicationContext());
        button.setBackgroundColor(getResources().getColor(R.color.inputLine));
        button.setText("删除");
        button.setTextColor(getColor(R.color.warning));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = mNavigationView.getMenu();
                menu.removeItem(i);
                //移动i以下的item
                ArrayList<String> titles = new ArrayList<>();
                for (int j = i+1;j<size;j++)
                {
                    titles.add(menu.getItem(i).getTitle().toString());
                    menu.removeItem(j);
                }
                for (int j = 0;j<titles.size();j++)
                {
                    addCollector(titles.get(j));
                }
                size = mNavigationView.getMenu().size();
                if (i-4 < operated.size()){
                    operated.remove(i-4);
                }
                else {
                    added.remove(i-4-operated.size());
                }
            }
        });
        item.setActionView(button);
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
                if (operated.size()<origin.size()){
                    //说明原始的collector有被删除
                    ArrayList<Integer> indexs = new ArrayList<>();
                    for (int i = 0;i<origin.size();i++)
                    {
                        if (!operated.contains(origin.get(i))){
                            indexs.add(i);
                        }
                    }
                    intent.putIntegerArrayListExtra("indexs",indexs);
                }
                if (added.size()>0){
                    //说明有新增的列
                    intent.putStringArrayListExtra("added",added);
                }
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
                if (operated.size()<origin.size()){
                    //说明原始的collector有被删除
                    ArrayList<Integer> indexs = new ArrayList<>();
                    for (int i = 0;i<origin.size();i++)
                    {
                        if (!operated.contains(origin.get(i))){
                            indexs.add(i);
                        }
                    }
                    intent.putIntegerArrayListExtra("indexs",indexs);
                }
                if (added.size()>0){
                    //说明有新增的列
                    intent.putStringArrayListExtra("added",added);
                }
                intent.putExtra("index",1);
                setResult(RESULT_OK,intent);
                this.finish();
                break;
            }
            case R.id.nav_item_courses:
            {
                item.setChecked(true);
                Intent intent = new Intent(this, MainActivity.class);
                if (operated.size()<origin.size()){
                    //说明原始的collector有被删除
                    ArrayList<Integer> indexs = new ArrayList<>();
                    for (int i = 0;i<origin.size();i++)
                    {
                        if (!operated.contains(origin.get(i))){
                            indexs.add(i);
                        }
                    }
                    intent.putIntegerArrayListExtra("indexs",indexs);
                }
                if (added.size()>0){
                    //说明有新增的列
                    intent.putStringArrayListExtra("added",added);
                }
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
                if (id<mNavigationView.getMenu().size()){
                    item.setChecked(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    if (operated.size()<origin.size()){
                        //说明原始的collector有被删除
                        ArrayList<Integer> indexs = new ArrayList<>();
                        for (int i = 0;i<origin.size();i++)
                        {
                            if (!operated.contains(origin.get(i))){
                                indexs.add(i);
                            }
                        }
                        intent.putIntegerArrayListExtra("indexs",indexs);
                    }
                    if (added.size()>0){
                        //说明有新增的列
                        intent.putStringArrayListExtra("added",added);
                    }
                    intent.putExtra("index",id-1);
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
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText text = view.findViewById(R.id.editText);
                String title = text.getText().toString();
                if (text.getText().length()>0&&!(operated.contains(title)||added.contains(title))) {
                    //布局里添加一行
                    addCollector(text.getText().toString());
                    size = mNavigationView.getMenu().size();
                    added.add(text.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),title+"已存在",Toast.LENGTH_SHORT);
                }
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
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void addCollector(String name)
    {
        int id = mNavigationView.getMenu().size();
        mNavigationView.getMenu().add(R.id.group_collections,id,id,name);
        mNavigationView.getMenu().getItem(id).setIcon(R.drawable.nav_default);
        mNavigationView.setNavigationItemSelectedListener(this);
        setClickDeleteListener(id);
    }
}
