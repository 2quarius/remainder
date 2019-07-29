package com.example.trail.Lists;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.runner.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.example.trail.Utils.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

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
    }
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
    public int getRecycler(int recyclerId) {
        RecyclerView recyclerView = (RecyclerView) rule
                .getActivity().findViewById(recyclerId);
        return recyclerView.getAdapter().getItemCount();
    }
    @Test
    public void clickCheckBox() throws Exception {
        Random random = new Random();
        if (getRecycler(R.id.my_recycler_view)>0) {
            onView(withRecyclerView(R.id.my_recycler_view).atPosition(random.nextInt(getRecycler(R.id.my_recycler_view)))).perform(longClick());
        }
    }
//    @Test
    @Deprecated
    public void swipeDelete() throws Exception {
        int first=getRecycler(R.id.my_recycler_view);
        onView(withRecyclerView(R.id.my_recycler_view).atPosition(0))
                .check(matches(isDisplayed()))
                .perform(swipeLeft());
        if(getRecycler(R.id.my_recycler_view)!=first-1){
            throw new Exception("delete error");
        }


    }
    @Test
    public void DragItem() throws Exception {
        Random random = new Random();
        if (getRecycler(R.id.my_recycler_view)>0) {
            onView(withRecyclerView(R.id.my_recycler_view).atPosition(random.nextInt(getRecycler(R.id.my_recycler_view))))
                    .perform(longClick());
        }
        //RecyclerViewActions.actionOnItemAtPosition(0,longClick());

    }
    @Test
    public void onCreateView() {
    }
}

