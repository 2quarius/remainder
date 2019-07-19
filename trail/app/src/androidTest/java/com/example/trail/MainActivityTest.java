package com.example.trail;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.trail.Utility.EnumPack.TabConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
        closeSoftKeyboard();
        //return to main activity
        pressBack();
        onView(isRoot()).perform(swipeRight());
        //return to main activity
        pressBack();
    }
    @Test
    public void testDisplay()
    {
        onView(withId(R.id.my_recycler_view))
                .check(matches(isDisplayed()))
                .check(matches(isFocusable()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(ViewMatchers.withText(TabConstants.TIME.getTitle())).perform(click());
        onView(withId(R.id.calendar_card_view))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(ViewMatchers.withText(TabConstants.SPACE.getTitle())).perform(click());
        onView(withId(R.id.map_card_view))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(ViewMatchers.withText(TabConstants.SETTING.getTitle())).perform(click());
        onView(withId(R.id.setting))
                .check(matches(isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
    }
}