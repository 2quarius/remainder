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

import com.example.trail.R;

public class SettingFragmnet extends Fragment {
    private Button btnAccountSet;
    private AccountFragment accountFragment;
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
        btnAccountSet=view.findViewById(R.id.btn_accountSetting);
        btnAccountSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accountFragment==null){
                    accountFragment= new AccountFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.viewpager,accountFragment).commitAllowingStateLoss();
            }
        });
    }
}
