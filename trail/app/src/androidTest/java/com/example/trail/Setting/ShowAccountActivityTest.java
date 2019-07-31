package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import com.example.trail.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ShowAccountActivityTest {
    @Rule
    public ActivityTestRule<ShowAccountActivity> rule=new ActivityTestRule<ShowAccountActivity>(ShowAccountActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.btn_logout)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.btn_showAccountBack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
    }

    @Test
    public void onStart() {
    }
}