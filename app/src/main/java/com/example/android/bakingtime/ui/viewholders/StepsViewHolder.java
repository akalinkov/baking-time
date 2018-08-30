package com.example.android.bakingtime.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.listeners.OnStepsItemClickListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = StepsViewHolder.class.getSimpleName();
    private OnStepsItemClickListener mCallback;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    @BindView(R.id.step_number)
    TextView mStepNumber;

    @BindView(R.id.thumbnail)
    ImageView mThumbnail;

    public StepsViewHolder(View itemView, OnStepsItemClickListener stepClickListener) {
        super(itemView);
        mCallback = stepClickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Step step) {
        Log.d(TAG, "bind: step #" + step.id);
        mDescription.setText(step.shortDescription);
        mStepNumber.setText(String.valueOf(step.id));
        try {

            Picasso.get()
                    .load(step.thumbnailURL)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(mThumbnail);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "bind: cannot load thumbnail for step #" + step.id
                    + " url=" + step.thumbnailURL);
        }
    }

    @Override
    public void onClick(View v) {
        mCallback.onStepClicked(getAdapterPosition());
    }
}
