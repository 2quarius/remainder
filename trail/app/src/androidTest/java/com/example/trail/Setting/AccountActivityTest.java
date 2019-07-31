package com.example.trail.Setting;


import androidx.test.rule.ActivityTestRule;

import com.example.trail.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class AccountActivityTest {

    @Test
    public void onStart() {
    }

    @Rule
    public ActivityTestRule<AccountActivity> rule=new ActivityTestRule<AccountActivity>(AccountActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.btn_login)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_regist)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_qq)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_wechat)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_jaccount)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_accountBack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.et_username)).check(matches(isDisplayed()));
        onView(withId(R.id.et_password)).check(matches(isDisplayed()));
    }

    @Test
    public void usernameTest1() {
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("请输入用户名"))
                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
    @Test
    public void usernameTest2() {
        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("请输入密码"))
                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
    //虚拟机无法连接bmob，无法测试
//    @Test
//    public void loginTest1() {
//        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
//        onView(withId(R.id.et_password)).perform(typeText("password"),closeSoftKeyboard());
//        onView(withId(R.id.btn_login)).perform(click());
//        onView(withText("登录成功"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void loginTest2() {
//        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
//        onView(withId(R.id.et_password)).perform(typeText("passwor"),closeSoftKeyboard());
//        onView(withId(R.id.btn_login)).perform(click());
//        onView(withText("密码错误，请重新输入"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void loginTest3() {
//        onView(withId(R.id.et_username)).perform(typeText("usernam"),closeSoftKeyboard());
//        onView(withId(R.id.et_password)).perform(typeText("password"),closeSoftKeyboard());
//        onView(withId(R.id.btn_login)).perform(click());
//        onView(withText("不存在此用户，请注册"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }

}