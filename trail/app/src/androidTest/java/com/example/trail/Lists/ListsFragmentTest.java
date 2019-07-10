package com.example.trail.Lists;

import com.example.trail.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.trail.R;
import com.example.trail.Utils.RecyclerViewMatcher;

public class ListsFragmentTest {
    @Rule
    public FragmentTestRule<MainActivity,ListsFragment> rule = new FragmentTestRule<>(MainActivity.class,ListsFragment.class);
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void onCreate(){
        onView(withRecyclerView(R.id.my_recycler_view).atPosition(3)).perform(click());
    }
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}