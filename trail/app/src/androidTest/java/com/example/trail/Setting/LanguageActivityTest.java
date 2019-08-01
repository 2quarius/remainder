//package com.example.trail.Setting;
//
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.trail.R;
//
//import org.junit.Rule;
//import org.junit.Test;
//
//import static androidx.test.espresso.action.ViewActions.click;
//import static org.junit.Assert.*;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//public class LanguageActivityTest {
//    @Rule
//    public ActivityTestRule<LanguageActivity> rule=new ActivityTestRule<>(LanguageActivity.class);
//
//    @Test
//    public void onCreate() {
//        onView(withId(R.id.cb_Chinese)).perform(click());
//        onView(withId(R.id.cb_english)).perform(click());
//    }
//}