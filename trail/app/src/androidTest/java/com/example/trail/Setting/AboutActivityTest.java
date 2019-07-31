package com.example.trail.Setting;

import com.example.trail.R;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class AboutActivityTest {

    @Test
    public void onCreate() {
        onView(withId(R.id.btn_aboutBack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.rl_head)).check(matches(isDisplayed()));
    }
}