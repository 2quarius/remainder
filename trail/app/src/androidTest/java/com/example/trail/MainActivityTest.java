package com.example.trail;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.KeyEventAction;
import androidx.test.espresso.action.MotionEvents;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.trail.Utility.TabConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onCreate() {
        onView(ViewMatchers.withText(TabConstants.TIME.getTitle())).perform(click());
        onView(ViewMatchers.withText(TabConstants.SPACE.getTitle())).perform(click());
        onView(ViewMatchers.withText(TabConstants.SETTING.getTitle())).perform(click());
        onView(ViewMatchers.withText(TabConstants.LISTS.getTitle())).perform(click());
        onView(ViewMatchers.withId(R.id.fab_addTask)).perform(click());
        //close keyboard
        pressBack();
        //return to main activity
        pressBack();
        onView(isRoot()).perform(swipeRight());
        //return to main activity
        pressBack();
    }
}