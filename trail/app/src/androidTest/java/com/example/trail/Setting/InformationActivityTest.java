//package com.example.trail.Setting;
//
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.trail.R;
//
//import org.junit.Rule;
//import org.junit.Test;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static org.junit.Assert.*;
//
//public class InformationActivityTest {
//    private String name="name";
//    private String age="age";
//    private String gender="gender";
//    private String phone="phone";
//    @Rule
//    public ActivityTestRule<InformationActivity> rule=new ActivityTestRule<>(InformationActivity.class);
//    @Test
//    public void onCreate() {
//        onView(withId(R.id.et_name)).perform(typeText(name),closeSoftKeyboard());
//        onView(withId(R.id.et_age)).perform(typeText(age),closeSoftKeyboard());
//        onView(withId(R.id.et_gender)).perform(typeText(gender),closeSoftKeyboard());
//        onView(withId(R.id.et_phone)).perform(typeText(phone),closeSoftKeyboard());
//    }
//    @Test
//    public void getTest(){
//        onView(withId(R.id.et_name)).perform(typeText(name),closeSoftKeyboard());
//        onView(withId(R.id.et_age)).perform(typeText(age),closeSoftKeyboard());
//        onView(withId(R.id.et_gender)).perform(typeText(gender),closeSoftKeyboard());
//        onView(withId(R.id.et_phone)).perform(typeText(phone),closeSoftKeyboard());
//        assertEquals(name,rule.getActivity().getName());
//        assertEquals(age,rule.getActivity().getAge());
//        assertEquals(gender,rule.getActivity().getGender());
//        assertEquals(phone,rule.getActivity().getphone());
//    }
//}