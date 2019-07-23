package com.example.trail.Setting;

import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.R;

import org.junit.Before;
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
    private Button btnLogin;
    private Button btnReg;
    private AccountActivity activity;
    private String username="username";
    private String password="password";
    @Rule
    public ActivityTestRule<AccountActivity> rule=new ActivityTestRule<AccountActivity>(AccountActivity.class);
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onCreate() {
        onView(withId(R.id.et_password)).check(matches(isDisplayed()));
        onView(withId(R.id.et_username)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_register))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_login))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));



    }

    @Test
    public void regTest(){
        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("pssword"),closeSoftKeyboard());
        onView(withId(R.id.btn_register)).perform(click());
        onView(withText("注册成功"))
                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
    @Test
    public void loginTest(){
        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("pssword"),closeSoftKeyboard());
        onView(withId(R.id.btn_register)).perform(click());
        onView(withText("注册成功"))
                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("pssword"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withText("登录成功"))
                .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
    @Test
    public void getUsernameTest(){
        onView(withId(R.id.et_username)).perform(typeText(username),closeSoftKeyboard());
        String results=rule.getActivity().getUsername();
        assertEquals(username,results);
    }

    @Test
    public void getPasswordTest(){
        onView(withId(R.id.et_password)).perform(typeText(password),closeSoftKeyboard());
        String results=rule.getActivity().getPassword();
        assertEquals(password,results);

    }

    @Test
    public void searchAccountTest(){
        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("password"),closeSoftKeyboard());
        onView(withId(R.id.btn_register)).perform(click());
        assertEquals(rule.getActivity().searchAccount(username),true);
    }

    @Test
    public void searchPasswordTest(){
        onView(withId(R.id.et_username)).perform(typeText("username"),closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("password"),closeSoftKeyboard());
        onView(withId(R.id.btn_register)).perform(click());
        assertEquals(rule.getActivity().searchPassword(username,password),true);
    }
}