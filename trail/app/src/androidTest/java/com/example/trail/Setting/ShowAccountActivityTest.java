package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.MainActivity;
import com.example.trail.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

public class ShowAccountActivityTest {
    private String username = "username";
    private String password = "password";
    @Rule
    public ActivityTestRule<ShowAccountActivity> rule=new ActivityTestRule<>(ShowAccountActivity.class);
    @Rule
    public ActivityTestRule<MainActivity> rule1=new ActivityTestRule<>(MainActivity.class);
    @Before
    public void login(){
        if (!BmobUser.isLogin()){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    System.out.println("sign up user:"+e.getMessage());
                }
            });
        }
    }
    @Test
    public void onCreate(){
        onView(withId(R.id.account_view))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.account_username))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.btn_back))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_logout))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
    }
    @Test
    public void testLogout(){
        if (BmobUser.isLogin()){
            onView(withId(R.id.btn_logout))
                    .perform(click());
            assertEquals(null,BmobUser.getCurrentUser(User.class));
            onView(withId(R.id.account_view))
                    .check(doesNotExist());
            onView(withId(R.id.account_username))
                    .check(doesNotExist());
            onView(withId(R.id.btn_back))
                    .check(doesNotExist());
            onView(withId(R.id.btn_logout))
                    .check(doesNotExist());
        }
    }
}