package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.MainActivity;
import com.example.trail.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ThemeActivityTest {
    private int defaultTheme = 2131099765;
    @Rule
    public ActivityTestRule<ThemeActivity> rule=new ActivityTestRule<>(ThemeActivity.class);
    @Rule
    public ActivityTestRule<MainActivity> rule1=new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testDisplay(){
        onView(withId(R.id.header_back))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.back))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.title))
                .check(matches(withText("主题")))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.default_theme))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.blue_theme))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
    }
}