package com.example.trail.Lists;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.trail.R;

public class ListsFragment extends Fragment {
    public static ListFragment newInstance(){
        return new ListFragment();
    }
    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_lists;
    }
}
