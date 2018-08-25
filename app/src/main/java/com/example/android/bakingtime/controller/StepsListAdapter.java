package com.example.android.bakingtime.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.OnItemClickListener;
import com.example.android.bakingtime.ui.details.StepsViewHolder;

import java.util.List;

import butterknife.BindView;

public class StepsListAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private List<Step> mStepsList;
    private Context mContext;
    private OnItemClickListener mStepsCallback;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    public StepsListAdapter(Context context, @NonNull List<Step> stepsList, OnItemClickListener stepClickListener) {
        mContext = context;
        mStepsList = stepsList;
        mStepsCallback = stepClickListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stepView = LayoutInflater.from(mContext).inflate(R.layout.item_step, parent, false);
        return new StepsViewHolder(stepView, mStepsCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(mStepsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (null == mStepsList) {
            return 0;
        }
        return mStepsList.size();
    }
}
