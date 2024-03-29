package com.example.trail.Setting;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.trail.MainActivity;
import com.example.trail.R;
import com.example.trail.Utility.UIHelper.AccountView.AccountView;
import com.example.trail.Utility.Utils.BmobUtils;
import com.example.trail.Utility.Utils.DESUtils;

import cn.bmob.v3.BmobUser;
import solid.ren.skinlibrary.base.SkinBaseActivity;

public class ShowAccountActivity extends SkinBaseActivity {
    private static final int REQUEST_CODE_CHOOSE = 20;
    private AccountView mAvatar;
    private TextView mUsername;
    private LinearLayout mBtnBack;
    private LinearLayout mBtnLogout;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_account);
        mAvatar = findViewById(R.id.account_view);
        mUsername = findViewById(R.id.account_username);
        User user = BmobUser.getCurrentUser(User.class);
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
            //TODO avatar
            mAvatar.setImageSource(R.drawable.dummy_image);
            mUsername.setText(username);
        }
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mBtnBack = findViewById(R.id.btn_back);
        mBtnLogout = findViewById(R.id.btn_logout);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAccountActivity.this.finish();
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.isLogin()){
                    BmobUtils.updateTaskCollector(MainActivity.taskCollectors);
                    BmobUser.logOut();
                    setResult(RESULT_CANCELED);
                    ShowAccountActivity.this.finish();
                }
            }
        });
    }
}
