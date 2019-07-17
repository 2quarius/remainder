package com.example.trail.Lists;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.runner.RunWith;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.trail.Lists.ListsFragment;
import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.example.trail.Utils.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.addGlobalAssertion;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ListsFragmentTest {
    private int LENGTH = 10;
    @Rule
    public FragmentTestRule<MainActivity, ListsFragment> rule =  new FragmentTestRule<>(MainActivity.class, ListsFragment.class);
    @Before
    public void init() throws Exception {
        List<Task> ts = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0;i<LENGTH;i++){
            random.setSeed(random.nextLong());
            String r = String.valueOf(random.nextInt());
            ts.add(new Task(r));
        }
        rule.getFragment().setTaskForTest(ts);
//
    }
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
    public int getRecycler(int recyclerId) {
        RecyclerView recyclerView = (RecyclerView) rule
                .getActivity().findViewById(recyclerId);
        return recyclerView.getAdapter().getItemCount();
    }
//    public static ViewAction click() {
//        return actionWithAssertions(
//                new GeneralClickAction(Tap.SINGLE, GeneralLocation.VISIBLE_CENTER, Press.FINGER));
//    }
    @Test
    public void clickCheckBox() throws Exception {
        //onView(allOf(withParent(withId(R.layout.recycler_view))qq,withId(R.id.card_view))).perform(scrollToPosition(4));
       //onView(allOf(withParent(withId(R.id.list_recycler_view)),withId(R.id.card_view))).perform(longClick());
        onView(withRecyclerView(R.id.my_recycler_view).atPosition(0)).perform(longClick());
        /*onView(withRecyclerView(R.id.my_recycler_view).atPosition(0).onChildView(withId(R.id.card_view)).onChildView(withId(R.id.checkBox)))
                .perform(click())
                .check(matches(isChecked()));*/

        //        //onView(withId(R.id.checkBox)).check(matches(isDisplayed()));
       // onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2,));


    }
    @Test
    public void swipeDelete() throws Exception {
        int first=getRecycler(R.id.my_recycler_view);
        onView(withRecyclerView(R.id.my_recycler_view).atPosition(1))
                .check(matches(isDisplayed()))
                .perform(swipeLeft());
        if(getRecycler(R.id.my_recycler_view)!=first-1){
            throw new Exception("delete error");
        }


    }
    @Test
    public void DragItem() throws Exception {
        onView(withRecyclerView(R.id.my_recycler_view).atPosition(0))
                .perform(longClick());
        //RecyclerViewActions.actionOnItemAtPosition(0,longClick());

    }
    @Test
    public void onCreateView() {
    }
}

