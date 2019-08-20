package com.example.trail.Setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trail.R;

import solid.ren.skinlibrary.SkinLoaderListener;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.loader.SkinManager;

public class ThemeActivity extends SkinBaseActivity {
    private ImageButton defaultTheme,blueTheme;
    private LinearLayout back;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        defaultTheme = findViewById(R.id.default_theme);
        blueTheme = findViewById(R.id.blue_theme);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeActivity.this.finish();
            }
        });
        title.setText(getResources().getString(R.string.theme));
        defaultTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkin("default.skin", new SkinLoaderListener() {
                    @Override
                    public void onStart() {
                        Log.i("SkinLoaderListener", "正在切换中");
                    }
                    @Override
                    public void onSuccess() {
                        Log.i("SkinLoaderListener", "切换成功");
                        Toast.makeText(getApplicationContext(),"切换成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailed(String errMsg) {
                        Log.i("SkinLoaderListener", "切换失败:" + errMsg);
                        Toast.makeText(getApplicationContext(),"切换失败:" + errMsg,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onProgress(int progress) {
                        Log.i("SkinLoaderListener", "皮肤文件下载中:" + progress);
                    }
                });
            }
        });
        blueTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkin("blue.skin", new SkinLoaderListener() {
                    @Override
                    public void onStart() {
                        Log.i("SkinLoaderListener", "正在切换中");
                    }
                    @Override
                    public void onSuccess() {
                        Log.i("SkinLoaderListener", "切换成功");
                        Toast.makeText(getApplicationContext(),"切换成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailed(String errMsg) {
                        Log.i("SkinLoaderListener", "切换失败:" + errMsg);
                        Toast.makeText(getApplicationContext(),"切换失败:" + errMsg,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onProgress(int progress) {
                        Log.i("SkinLoaderListener", "皮肤文件下载中:" + progress);
                    }
                });
            }
        });
    }
}
