//package com.example.trail.Setting;
//
//import android.widget.Button;
//
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.trail.R;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static org.junit.Assert.*;
//
//
//public class AccountActivityTest {
//    private Button btnLogin;
//    private Button btnReg;
//    private AccountActivity activity;
//    @Rule
//    public ActivityTestRule<AccountActivity> rule=new ActivityTestRule<AccountActivity>(AccountActivity.class);
//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @Test
//    public void onCreate() {
//        onView(withId(R.id.btn_login)).perform(click());
//        onView(withId(R.id.btn_register)).perform(click());
//    }
//
//    @Test
//    public void getUsernameTest(){
//        String username="username";
//        onView(withId(R.id.et_username)).perform(typeText(username),closeSoftKeyboard());
//        String results=rule.getActivity().getUsername();
//        assertEquals(username,results);
//    }
//
//    @Test
//    public void getPasswordTest(){
//        String password="password";
//        onView(withId(R.id.et_password)).perform(typeText(password),closeSoftKeyboard());
//        String results=rule.getActivity().getPassword();
//        assertEquals(password,results);
//
//    }
//}