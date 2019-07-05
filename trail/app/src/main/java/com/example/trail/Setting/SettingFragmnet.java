package com.example.trail.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trail.MainActivity;
import com.example.trail.R;

public class SettingFragmnet extends Fragment {
    private Button btnAccountSet;
    private Button btnThemeSet;
    private Button btnInformation;
    private Button btnLanguage;
    private Button btnVoice;
    private Button btnAbout;
    private SettingFragmnet settingFragmnet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_setting,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //跳转到Account界面
        btnAccountSet=view.findViewById(R.id.btn_accountSetting);
        btnAccountSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),AccountActivity.class);
                startActivity(intent);
            }

        });
        //跳转到主题设置界面
        btnThemeSet=view.findViewById(R.id.btn_theme);
        btnThemeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),ThemeActivity.class);
                startActivity(intent);
            }
        });
        //跳转到声音设置界面
        btnVoice=view.findViewById(R.id.btn_voice);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),VoiceActivity.class);
                startActivity(intent);
            }
        });
        //跳转到语言设置界面
        btnLanguage=view.findViewById(R.id.btn_language);
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),LanguageActivity.class);
                startActivity(intent);
            }
        });
        //跳转到信息设置界面
        btnInformation=view.findViewById(R.id.btn_information);
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),InformationActivity.class);
                startActivity(intent);
            }
        });
        //跳转到关于我们界面
        btnAbout=view.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
