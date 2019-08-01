package com.example.trail.Setting;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.trail.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class VoiceActivityTest {
    @Rule
    public ActivityTestRule<VoiceActivity> rule=new ActivityTestRule<>(VoiceActivity.class);
    @Test
    public void onCreate() {
        onView(withId(R.id.btn_voiceBack)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
        onView(withId(R.id.sb_voice0)).
                check(matches(isDisplayed()));
        onView(withId(R.id.sb_voice1)).
                check(matches(isDisplayed()));
        onView(withId(R.id.vibration)).
                check(matches(isDisplayed())).
                check(matches(isClickable()));
    }

    @Test
    public void voice0MaxTest(){
        SeekBar voice0=rule.getActivity().findViewById(R.id.sb_voice0);
        int maxVoice0=rule.getActivity().getMaxSystemVolume();
        int maxVoiceNow0=voice0.getMax();
        assertEquals(maxVoice0,maxVoiceNow0);
    }

    @Test
    public void voice1MaxTest(){
        SeekBar voice1=rule.getActivity().findViewById(R.id.sb_voice1);
        int maxVoice1=rule.getActivity().getMaxMusicVolume();
        int maxVoiceNow1=voice1.getMax();
        assertEquals(maxVoice1,maxVoiceNow1);
    }

    @Test
    public void voice0CurrentText(){
        SeekBar voice0=rule.getActivity().findViewById(R.id.sb_voice0);
        int currentVoice0=rule.getActivity().getSystemVolume();
        int currentVoiceNow0=voice0.getProgress();
        assertEquals(currentVoice0,currentVoiceNow0);
    }

    @Test
    public void voice1CurrentText(){
        SeekBar voice1=rule.getActivity().findViewById(R.id.sb_voice1);
        int currentVoice1=rule.getActivity().getMusicVolume();
        int currentVoiceNow1=voice1.getProgress();
        assertEquals(currentVoice1,currentVoiceNow1);
    }

    @Test
    public void voice0Test(){
        int voice=getProgress(withId(R.id.sb_voice0));
        setProgress(withId(R.id.sb_voice0),voice+1);
        int voice0=rule.getActivity().getSystemVolume();
        assertEquals(voice0,voice+1);
    }

    @Test
    public void voice1Test(){
        int voice=getProgress(withId(R.id.sb_voice1));
        setProgress(withId(R.id.sb_voice1),voice+1);
        int voice1=rule.getActivity().getMusicVolume();
        assertEquals(voice1,voice+1);
    }

    public int getProgress(Matcher<View> matcher) {

        final int[] progress = {0};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "seekbar";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar)view;
                progress[0] = seekBar.getProgress();
            }
        });
        return progress[0];
    }
    public void setProgress(Matcher<View> matcher,int pro) {

        final int[] progress = {0};
        progress[0]=pro;
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "seekbar";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar)view;
                seekBar.setProgress(progress[0]);
            }
        });
    }

}