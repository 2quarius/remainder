package com.example.trail.Lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.trail.R;

public class ListsFragment extends Fragment {
    public static ListFragment newInstance(){
        return new ListFragment();
    }
   /* @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_lists;
    }*/
   public ListsFragment(){}

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }
}
