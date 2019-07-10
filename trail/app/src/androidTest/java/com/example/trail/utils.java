package com.example.trail;



import android.view.View;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class utils {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    }
}
