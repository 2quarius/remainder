package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.R;

import org.junit.Rule;
import org.junit.Test;

import java.io.FileInputStream;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static cn.bmob.v3.Bmob.getApplicationContext;
import static org.junit.Assert.*;

public class ThemeActivityTest {
    @Rule
    public ActivityTestRule<ThemeActivity> rule=new ActivityTestRule<>(ThemeActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.btn_themeBlack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_themePurple)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_themeBack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
    }

    @Test
    public void themeTest(){
        onView(withId(R.id.btn_themeBlack)).perform(click());
        String theme=readTheme();
        assertEquals(theme,"black");
    }
    @Test
    public void themeTest1(){
        onView(withId(R.id.btn_themePurple)).perform(click());
        String theme=readTheme();
        assertEquals(theme,"purple");

    }
    private String readTheme(){
        String textContent = "";
        try {
            FileInputStream ios = getApplicationContext().openFileInput("theme.txt");
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            textContent = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
        return textContent;
    }
}