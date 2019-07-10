package com.example.trail;

import android.app.Activity;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.trail.Lists.ListsFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ListFragmentTest {
    @Rule
    public FragmentTestRule<?, ListsFragment> rule =  FragmentTestRule.create(ListsFragment.class);
    @Before
    public void setUp() throws Exception {

    }
//    /**
//     * 模拟用户的点击行为
//     *
//     * @param id
//     */
//    public void testClick(final int id) {
//        onView(withId(id)).perform(closeSoftKeyboard(), click());
//    }
    @Test
    public void clickCheckBox() throws Exception {
        onView(withId(R.id.checkBox)).perform(click());
        onView(withId(R.id.checkBox)).check(matches(isDisplayed()));

    }
    @Test
    public void swipeDelete() throws Exception {

    }

}

