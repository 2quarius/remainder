package com.example.trail.Calendar;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.runner.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.trail.MainActivity;
import com.example.trail.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class CalendarFragmentTest {
    @Rule
    public FragmentTestRule<MainActivity, CalendarFragment> rule = new FragmentTestRule<>(MainActivity.class, CalendarFragment.class);
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void testAttr(){
        onView(allOf(withId(R.id.rl_tool),isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(allOf(withId(R.id.tv_month_day),isDisplayed()))
                .check(matches(isClickable()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isFocusable()))
                .check(matches(isEnabled()));
        onView(allOf(withId(R.id.tv_lunar),isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(allOf(withId(R.id.tv_year),isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(allOf(withId(R.id.tv_current_day),isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        onView(allOf(withId(R.id.calendar_view),isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
    }
    @Test
    public void testPerform(){
        String s= new Date(System.currentTimeMillis()).toString();
        //点击跳转到年视图
        onView(allOf(withId(R.id.tv_month_day),isDisplayed())).perform(click());
        //检查年视图是否显示
        onView(allOf(withId(R.id.tv_month_day),isDisplayed())).check(matches(withText(s.substring(0,4))));
        onView(allOf(withId(R.id.calendar_view),isDisplayed())).check(matches(isCompletelyDisplayed()));
        //点击返回月视图
        onView(allOf(withId(R.id.calendar_view),isDisplayed())).perform(click());
        //左右拖动，检查是否到下一月视图
        onView(allOf(withId(R.id.calendar_view),isDisplayed())).perform(dragLeft2Right());
        onView(allOf(withId(R.id.calendar_view),isDisplayed()))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()));
        //点击右上角icon，检查是否回到当前月份
        onView(allOf(withId(R.id.tv_current_day),isDisplayed())).perform(click());
        onView(allOf(withId(R.id.tv_month_day),isDisplayed()))
                .check(matches(withText(CalendarFragment.constructMonthDay(Integer.valueOf(s.substring(5,7)),Integer.valueOf(s.substring(8,10))))));
        onView(allOf(withId(R.id.calendar_view),isDisplayed())).check(matches(isCompletelyDisplayed()));

    }
    public static ViewAction dragLeft2Right() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT,
                                      GeneralLocation.CENTER_RIGHT, Press.FINGER);
    }
}