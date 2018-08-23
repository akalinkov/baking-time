package com.example.android.bakingtime.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String STEP_FORMAT = "%d. %s";
    private OnItemClickListener mCallback;
    @BindView(R.id.tv_step_description)
    TextView mDescription;

    public StepsViewHolder(View itemView, OnItemClickListener stepClickListener) {
        super(itemView);
        mCallback = stepClickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Step step) {
        mDescription.setText(String.format(STEP_FORMAT, step.id, step.shortDescription));
    }

    @Override
    public void onClick(View v) {
        mCallback.onItemClicked(getAdapterPosition());
    }
}
