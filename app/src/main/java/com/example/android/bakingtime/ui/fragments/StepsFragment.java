package com.example.android.bakingtime.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.adapters.StepsListAdapter;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.listeners.OnStepsItemClickListener;

import java.util.List;

public class StepsFragment extends Fragment implements OnStepsItemClickListener {

    private static final String TAG = StepsFragment.class.getSimpleName();

    private List<Step> mStepsList;
    private OnStepsItemClickListener mCallback;
    private StepsListAdapter mStepsAdapter;

    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        RecyclerView stepsListView = rootView.findViewById(R.id.rv_steps_list);
        mStepsAdapter = new StepsListAdapter(getContext(), mStepsList, this);
        stepsListView.setAdapter(mStepsAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepsItemClickListener) context;
        } catch (ClassCastException e) {
            Log.d(TAG, String.format("onAttach: %s %s %s",
                    context.getClass().getSimpleName(),
                    " activity should implement interface",
                    OnStepsItemClickListener.class.getSimpleName()));
        }
    }

    public void setStepsList(List<Step> stepsList) {
        mStepsList = stepsList;
    }

    @Override
    public void onStepClicked(int position) {
        Log.d(TAG, "onStepClicked: #" + position);
        if (position == RecyclerView.NO_POSITION) return;
        mStepsAdapter.notifySelectedItemchanged();
        mStepsAdapter.setSelectedPosition(position);
        mStepsAdapter.notifyItemChanged(position);
        mCallback.onStepClicked(position);
    }
}
