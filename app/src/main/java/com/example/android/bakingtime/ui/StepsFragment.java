package com.example.android.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.controller.StepsListAdapter;
import com.example.android.bakingtime.model.Step;

import java.util.List;

public class StepsFragment extends Fragment {

    private List<Step> mStepsList;

    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        RecyclerView stepsListView = rootView.findViewById(R.id.rv_steps_list);
        StepsListAdapter stepsListAdapter = new StepsListAdapter(getContext(), mStepsList);
        stepsListView.setAdapter(stepsListAdapter);
        return rootView;
    }

    public void setStepsList(List<Step> stepsList) {
        mStepsList = stepsList;
    }
}
