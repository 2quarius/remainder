package com.example.trail.NewTask;

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

public class AddTaskActivityTest {

    @Rule
    public ActivityTestRule<AddTaskActivity> rule = new ActivityTestRule<AddTaskActivity>(AddTaskActivity.class);

    /*@Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }*/

    @Test
    public void onCreate() {
        onView(withId(R.id.title)).perform(typeText("The Title"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.description)).perform(typeText("The Description"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.expire_date_et)).perform(click());
        onView(ViewMatchers.withText("CANCEL")).perform(click());
        onView(withId(R.id.expire_date_et))
                .perform(click());
        onView(ViewMatchers.withText("2019"))
                .perform(click());
        onView(ViewMatchers.withText("2020"))
                .perform(swipeUp());
        onView(ViewMatchers.withText("2021"))
                .perform(click());
        //onView(ViewMatchers.withText("15")).perform(swipeUp());
        //onView(ViewMatchers.withText("23")).perform(click());
        onView(ViewMatchers.withText("OK"))
                .perform(click());

        onView(withId(R.id.expire_time_et)).perform(click());
        onView(ViewMatchers.withText("CANCEL")).perform(click());
        onView(withId(R.id.expire_time_et))
                .perform(click());
        //onView(ViewMatchers.withText("8")).perform(click());
        //onView(ViewMatchers.withText("35")).perform(click());
        //onView(ViewMatchers.withText("PM")).perform(click());
        onView(ViewMatchers.withText("OK"))
                .perform(click());

        onView(withId(R.id.remind_me_switch))
                .perform(click());

        onView(withId(R.id.remind_date)).perform(click());
        onView(ViewMatchers.withText("CANCEL")).perform(click());
        onView(withId(R.id.remind_date))
                .perform(click());
        onView(ViewMatchers.withText("OK"))
                .perform(click());

        onView(withId(R.id.remind_time)).perform(click());
        onView(ViewMatchers.withText("CANCEL")).perform(click());
        onView(withId(R.id.remind_time))
                .perform(click());
        onView(ViewMatchers.withText("OK"))
                .perform(click());

        onView(withId(R.id.repeat_type))
                .perform(click());
        onView(ViewMatchers.withText("每年"))
                .perform(click());

        onView(withId(R.id.send_task_fab))
                .perform(click());
    }

}