package com.example.trail.Setting;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class AboutActivityTest {
    @Rule
    public ActivityTestRule<AboutActivity> rule=new ActivityTestRule<>(AboutActivity.class);

    @Test
    public void onCreate() {
        onView(withText("有我在——ddl终结者")).
                check(matches(isDisplayed()));
        onView(withText("Version 6.2")).
                check(matches(isDisplayed()));
        onView(withText("访问网站")).
                check(matches(isDisplayed()));
        onView(withText("前往Twitter关注我们")).
                check(matches(isDisplayed()));
        onView(withText("前往YouTube观看")).
                check(matches(isDisplayed()));
        onView(withText("评分")).
                check(matches(isDisplayed()));
        onView(withText("前往Instagram关注我们"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("前往GitHub Fork项目"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("返回"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

}