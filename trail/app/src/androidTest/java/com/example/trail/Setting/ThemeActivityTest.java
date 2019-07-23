package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.util.regex.Pattern.matches;
import static org.junit.Assert.*;

public class ThemeActivityTest {
    @Rule
    public ActivityTestRule<ThemeActivity> rule=new ActivityTestRule<>(ThemeActivity.class);
    @Test
    public void onCreate() {
    }
}