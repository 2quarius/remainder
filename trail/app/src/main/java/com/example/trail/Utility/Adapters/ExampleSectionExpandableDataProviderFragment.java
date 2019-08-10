package com.example.trail.Utility.Adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ExampleSectionExpandableDataProviderFragment extends Fragment {
    private ExampleSectionExpandableDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new ExampleSectionExpandableDataProvider();
    }

    public AbstractExpandableDataProvider getDataProvider() {
        return mDataProvider;
    }
}
