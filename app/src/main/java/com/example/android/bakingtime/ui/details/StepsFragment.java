package com.example.android.bakingtime.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.controller.StepsListAdapter;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment implements OnItemClickListener {

    private static final String TAG = StepsFragment.class.getSimpleName();

    private List<Step> mStepsList;
    private OnItemClickListener mCallback;

    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        RecyclerView stepsListView = rootView.findViewById(R.id.rv_steps_list);
        StepsListAdapter stepsListAdapter = new StepsListAdapter(getContext(), mStepsList, this);
        stepsListView.setAdapter(stepsListAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            Log.d(TAG, String.format("onAttach: %s %s %s",
                    context.getClass().getSimpleName(),
                    " activity should implement interface",
                    OnItemClickListener.class.getSimpleName()));
        }
    }

    public void setStepsList(List<Step> stepsList) {
        mStepsList = stepsList;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getContext(), StepDetailsActivity.class);
        intent.putParcelableArrayListExtra(Step.SAVED_INTENT, (ArrayList<? extends Parcelable>) mStepsList);
        intent.putExtra(Step.CURRENT, position);
        startActivity(intent);
    }
}
