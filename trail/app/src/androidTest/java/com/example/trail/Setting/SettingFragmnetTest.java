//package com.example.trail.Setting;
//
//
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.trail.MainActivity;
//import com.example.trail.R;
//import com.example.trail.Utility.EnumPack.TabConstants;
//
//import org.junit.Rule;
//import org.junit.Test;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.Espresso.pressBack;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.*;
//import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//public class SettingFragmnetTest {
//    @Rule
//    public ActivityTestRule<MainActivity> rule=new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void onCreateView() {
//        onView(withId(R.id.fab_addTask)).check(matches(isClickable()));
//    }
//
//    @Test
//    public void onViewCreated() {
//        onView(withText(TabConstants.SETTING.getTitle())).perform(click());
//        onView(withId(R.id.btn_about))
//                .check(matches(isDisplayed()))
//                .check(matches(isClickable()))
//                .perform(click());
//        pressBack();//back to setting
//        onView(withId(R.id.btn_language))
//                .check(matches(isDisplayed()))
//                .check(matches(isClickable()))
//                .perform(click());
//        pressBack();//back
//        onView(withId(R.id.btn_accountSetting))
//                .check(matches(isDisplayed()))
//                .check(matches(isClickable()))
//                .perform(click());
//        pressBack();//back
//        onView(withId(R.id.btn_voice))
//                .check(matches(isDisplayed()))
//                .check(matches(isClickable()))
//                .perform(click());
//        pressBack();//back
//        onView(withId(R.id.btn_theme))
//                .check(matches(isDisplayed()))
//                .check(matches(isClickable()))
//                .perform(click());
//        pressBack();//back
//        onView(withId(R.id.btn_information))
//                .check(matches(isDisplayed()))
//                .check(matches(isClickable()))
//                .perform(click());
//        pressBack();//back
//    }
//
//    @Test
//    public void onDestroyView() {
//        onView(withId(R.id.fab_addTask)).check(matches(isClickable()));
//    }
//}