package com.example.trail.Lists;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.trail.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class SideMenuActivityTest {
    @Rule
    public ActivityTestRule<SideMenuActivity> rule = new ActivityTestRule<>(SideMenuActivity.class);

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void testNavMenuClick() {
        onView(ViewMatchers.withText(R.string.add_list_file)).perform(click());
        onView(ViewMatchers.withText("添加")).perform(click());
        onView(ViewMatchers.withText(R.string.add_list_file)).perform(click());
        onView(ViewMatchers.withText("取消")).perform(click());
        onView(ViewMatchers.withText(R.string.nav_today)).perform(click());
        onView(isRoot()).perform(swipeRight());
        onView(ViewMatchers.withText(R.string.nav_collection)).perform(click());
    }
    @Test
    public void testNavMenuDisplay(){
        onView(ViewMatchers.withText(R.string.add_list_file))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDescendantOfA(withId(R.id.nav_view))));
        onView(ViewMatchers.withText(R.string.add_list_file)).perform(click());
        onView(ViewMatchers.withText("添加"))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isFocusable()));
        onView(ViewMatchers.withText("取消"))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isFocusable()));
        onView(ViewMatchers.withText("添加清单"))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withText("取消")).perform(click());
        onView(ViewMatchers.withText(R.string.nav_today))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDescendantOfA(withId(R.id.nav_view))));
        onView(ViewMatchers.withText(R.string.nav_collection))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDescendantOfA(withId(R.id.nav_view))));
    }
    @Test
    public void testNavHeadDisplay(){
        onView(ViewMatchers.withId(R.id.nav_view)).check(matches(isDisplayed()));
    }
}