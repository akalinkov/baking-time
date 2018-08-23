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
import com.example.android.bakingtime.ui.StepsViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StepsListAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private List<Step> mStepsList;
    private Context mContext;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    public StepsListAdapter(Context context, @NonNull List<Step> stepsList) {
        mContext = context;
        mStepsList = stepsList;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_step, parent, false);
        return new StepsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(mStepsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }
}
