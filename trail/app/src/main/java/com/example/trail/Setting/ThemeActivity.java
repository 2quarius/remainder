package com.example.trail.Setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.trail.R;

import solid.ren.skinlibrary.SkinLoaderListener;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.loader.SkinManager;

public class ThemeActivity extends SkinBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ImageButton button = findViewById(R.id.btn_themeBlack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SkinManager.getInstance().nightMode();
                SkinManager.getInstance().loadSkin("default.skin", new SkinLoaderListener() {
                    @Override
                    public void onStart() {
                        Log.i("SkinLoaderListener", "正在切换中");
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("SkinLoaderListener", "切换成功");
                    }

                    @Override
                    public void onFailed(String errMsg) {
                        Log.i("SkinLoaderListener", "切换失败:" + errMsg);
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
