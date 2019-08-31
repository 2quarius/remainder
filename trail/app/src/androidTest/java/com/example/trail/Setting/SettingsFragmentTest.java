package com.example.trail.Setting;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.trail.MainActivity;
import com.example.trail.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SettingsFragmentTest {
    @Rule
    public FragmentTestRule<MainActivity, SettingsFragment> rule = new FragmentTestRule<>(MainActivity.class, SettingsFragment.class);
    @Test
    public void onCreate(){
        onView(withId(R.id.account))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.theme))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.voice))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.guard))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.assign))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.about))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isClickable()));
    }
    @Test
    public void testClick(){
        onView(withId(R.id.account))
                    .perform(click());
        onView(withId(R.id.fragment_settings))
                .check(doesNotExist());
        onView(withText("返回"))
                    .perform(click());
        onView(withId(R.id.theme))
                .perform(click());
        onView(withId(R.id.fragment_settings))
                .check(doesNotExist());
        onView(withId(R.id.back))
                .perform(click());
        onView(withId(R.id.assign))
                .perform(click());
        onView(withId(R.id.fragment_settings))
                .check(doesNotExist());
        onView(withId(R.id.back))
                .perform(click());
        onView(withId(R.id.about))
                .perform(click());
        onView(withId(R.id.fragment_settings))
                .check(doesNotExist());
        onView(withText("返回"))
                .perform(scrollTo(),click());
    }
}