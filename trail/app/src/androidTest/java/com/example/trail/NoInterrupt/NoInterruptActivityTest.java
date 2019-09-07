package com.example.trail.NoInterrupt;

import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.example.trail.R;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.typeText;
import static java.util.regex.Pattern.matches;

public class NoInterruptActivityTest {

    @Rule
    public ActivityTestRule<NoInterruptActivity> rule = new ActivityTestRule<NoInterruptActivity>(NoInterruptActivity.class);

    @Test
    public void onCreate() {

        onView(withId(R.id.btn_music)).perform(click());

        onView(withId(R.id.btn_music)).perform(click());

        onView(withId(R.id.btn_end)).perform(swipeRight());

    }

}