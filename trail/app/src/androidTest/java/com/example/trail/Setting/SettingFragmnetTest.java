package com.example.trail.Setting;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import com.example.trail.MainActivity;
import com.example.trail.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class SettingFragmnetTest {
    @Rule
    public ActivityTestRule<MainActivity> rule=new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onCreateView() {
//        onView(withId(R.id.card_title)).perform(click());
//        FragmentManager fm;
//        fm=rule.getActivity().getSupportFragmentManager();
//        FragmentTransaction ft;
//        ft=fm.beginTransaction();
//        SettingFragmnet settingFragmnet=new SettingFragmnet();
//        ft.replace(R.layout.fragment_lists,settingFragmnet);
//        onView(withId(R.id.btn_about)).perform(click());
        onView(withText())
        onView(withId(R.id.btn_about)).perform(click());
    }

    @Test
    public void onViewCreated() {
    }

    @Test
    public void onDestroyView() {
    }
}