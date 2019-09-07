package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.MainActivity;
import com.example.trail.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import cn.bmob.v3.BmobUser;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest {
    private String username = "username";
    private String password = "password";
    @Rule
    public ActivityTestRule<LoginActivity> rule=new ActivityTestRule<>(LoginActivity.class);
    @Rule
    public ActivityTestRule<MainActivity> rule1=new ActivityTestRule<>(MainActivity.class);

    @Before
    public void logout(){
        if (BmobUser.isLogin()){
            BmobUser.logOut();
        }
    }
    @Test
    public void onCreate() {
        onView(withId(R.id.et_account))
                .check(matches(isDisplayed()))
                .check(matches(withHint("请输入用户名")))
                .check(matches(isFocusable()));
        onView(withId(R.id.et_pwd))
                .check(matches(isDisplayed()))
                .check(matches(withHint("请输入密码")))
                .check(matches(isFocusable()));
        onView(withId(R.id.btn_login))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_register))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_qq))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_weixin))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_jaccount))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
    }

}