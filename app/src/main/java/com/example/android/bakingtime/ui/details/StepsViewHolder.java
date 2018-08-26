package com.example.android.bakingtime.ui.details;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.OnStepsItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnStepsItemClickListener mCallback;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    @BindView(R.id.step_number)
    TextView mStepNumber;

    public StepsViewHolder(View itemView, OnStepsItemClickListener stepClickListener) {
        super(itemView);
        mCallback = stepClickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Step step) {
        mDescription.setText(step.shortDescription);
        mStepNumber.setText(String.valueOf(step.id));
    }

    @Override
    public void onClick(View v) {
        mCallback.onStepClicked(getAdapterPosition());
    }
}
