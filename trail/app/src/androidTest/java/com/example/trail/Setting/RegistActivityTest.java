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

public class RegistActivityTest {
    @Rule
    public ActivityTestRule<RegistActivity> rule=new ActivityTestRule<RegistActivity>(RegistActivity.class);

    private String username="user";
    private String password="Password";
    @Test
    public void onCreate() {
        onView(withId(R.id.btn_registBack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_regist1)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.et_password1)).
                check(matches(isDisplayed()));
        onView(withId(R.id.et_username1)).
                check(matches(isDisplayed()));
        onView(withId(R.id.et_passwordAgain)).
                check(matches(isDisplayed()));
    }

    //虚拟机无法连接bmob 测试无法进行
//    @Test
//    public void registTest1() {
//        onView(withId(R.id.btn_regist1)).perform(click());
//        onView(withText("请输入用户名"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void registTest2() {
//        onView(withId(R.id.et_username1)).perform(typeText(username),closeSoftKeyboard());
//        onView(withId(R.id.btn_regist1)).perform(click());
//        onView(withText("请输入密码"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void registTest3() {
//        onView(withId(R.id.et_username1)).perform(typeText(username),closeSoftKeyboard());
//        onView(withId(R.id.et_password1)).perform(typeText("pass"),closeSoftKeyboard());
//        onView(withId(R.id.btn_regist1)).perform(click());
//        onView(withText("密码长度需大于六位"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void registTest4() {
//        onView(withId(R.id.et_username1)).perform(typeText(username),closeSoftKeyboard());
//        onView(withId(R.id.et_password1)).perform(typeText(password),closeSoftKeyboard());
//        onView(withId(R.id.btn_regist1)).perform(click());
//        onView(withText("请再次输入密码"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void registTest5() {
//        onView(withId(R.id.et_username1)).perform(typeText(username),closeSoftKeyboard());
//        onView(withId(R.id.et_password1)).perform(typeText(password),closeSoftKeyboard());
//        onView(withId(R.id.et_passwordAgain)).perform(typeText("pass"),closeSoftKeyboard());
//        onView(withId(R.id.btn_regist1)).perform(click());
//        onView(withText("两次密码不一致，请重新输入"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void registTest6() {
//        onView(withId(R.id.et_username1)).perform(typeText(username),closeSoftKeyboard());
//        onView(withId(R.id.et_password1)).perform(typeText(password),closeSoftKeyboard());
//        onView(withId(R.id.et_passwordAgain)).perform(typeText(password),closeSoftKeyboard());
//        onView(withId(R.id.btn_regist1)).perform(click());
//        onView(withText("注册成功"))
//                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//    }

}