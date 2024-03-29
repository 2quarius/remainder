package com.example.trail.Setting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.trail.R;
import com.example.trail.Setting.AssignWithML.AssignActivity;
import com.example.trail.Setting.NoInterrupt.NoInterruptActivity;
import com.example.trail.Utility.Utils.DESUtils;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import cn.bmob.v3.BmobUser;
import solid.ren.skinlibrary.base.SkinBaseFragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends SkinBaseFragment {
    private static final int REQUEST_LOGIN = 11;
    private static final int REQUEST_SHOW_ACCOUNT = 12;
    private static final int REQUEST_THEME = 13;
    private static final int REQUEST_VOICE = 14;
    private static final int REQUEST_ABOUT = 15;
    private static final int REQUEST_GUARD = 16;
    private static final int REQUEST_ASSIGN = 17;
    private static final String HAVE_NOT_LOGIN = "暂未登录";
    private RelativeLayout mAccount;
    private RelativeLayout mTheme;
    private RelativeLayout mVoice;
    private RelativeLayout mGuard;
    private RelativeLayout mAssign;
    private RelativeLayout mAbout;
    private AvatarView mAccountPic;
    private IImageLoader imageLoader;
    private TextView mAccountName;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(view);
        addListener();
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==REQUEST_LOGIN&&resultCode==RESULT_OK){
            setUser();
            //TODO 更新MainActivity里的user
        }
        if (requestCode==REQUEST_SHOW_ACCOUNT&&resultCode==RESULT_CANCELED){
            setUser();
            //TODO 更新MainActivity里的user
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUser(){
        user = BmobUser.getCurrentUser(User.class);
        if (user!=null){
            String username = null;
            if (user.getAccessToken()!=null){
                try {
                    username = DESUtils.decrypt(user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                username = user.getUsername();
            }
            //TODO 更新头像
            imageLoader.loadImage(mAccountPic, "http:/example.com/user/someUserAvatar.png", username);
            mAccountName.setText(username);
        }
        else {
            mAccountPic.setVisibility(View.VISIBLE);
            imageLoader.loadImage(mAccountPic, "http:/example.com/user/someUserAvatar.png", "User Name");
            mAccountName.setText(HAVE_NOT_LOGIN);
        }
    }

    private void addListener() {
        mAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user==null) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_LOGIN);
                }
                else {
                    startActivityForResult(new Intent(getActivity(),ShowAccountActivity.class),REQUEST_SHOW_ACCOUNT);
                }
            }
        });
        mTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),ThemeActivity.class),REQUEST_THEME);
            }
        });
        mVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),VoiceActivity.class),REQUEST_VOICE);
            }
        });
        mGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), NoInterruptActivity.class), REQUEST_GUARD);
            }
        });
        mAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AssignActivity.class), REQUEST_ASSIGN);
            }
        });
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),AboutActivity.class),REQUEST_ABOUT);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView(View view) {
        mAccount = view.findViewById(R.id.account);
        mTheme = view.findViewById(R.id.theme);
        mVoice = view.findViewById(R.id.voice);
        mGuard = view.findViewById(R.id.guard);
        mAssign = view.findViewById(R.id.assign);
        mAbout = view.findViewById(R.id.about);
        mAccountPic = view.findViewById(R.id.account_pic);
        imageLoader = new PicassoLoader();
        mAccountName = view.findViewById(R.id.account_name);
        setUser();
    }

}
