package com.example.trail;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.trail.Utility.TabConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onCreate() {
        Espresso.onView(ViewMatchers.withText(TabConstants.TIME.getTitle())).perform(click());
        Espresso.onView(ViewMatchers.withText(TabConstants.SPACE.getTitle())).perform(click());
        Espresso.onView(ViewMatchers.withText(TabConstants.SETTING.getTitle())).perform(click());
        Espresso.onView(ViewMatchers.withText(TabConstants.LISTS.getTitle())).perform(click());
    }
}